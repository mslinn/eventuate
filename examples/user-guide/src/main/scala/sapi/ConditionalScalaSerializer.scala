/*
 * Copyright 2015 - 2017 Red Bull Media House GmbH <http://www.redbullmediahouse.com> and Mike Slinn - all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

//#scala-serializer
package sapi

import java.nio.ByteBuffer
import akka.serialization._
import EventsourcedViews._

object ConditionalScalaSerializer {
  val UTF_8: String = java.nio.charset.StandardCharsets.UTF_8.name

  val GetAppendCountManifest: String       = GetAppendCount.getClass.getName        // "sapi.EventsourcedViews$GetAppendCount$"
  val GetResolveCountManifest: String      = GetResolveCount.getClass.getName       // "sapi.EventsourcedViews$GetResolveCount$"
  val GetResolveCountReplyManifest: String = classOf[GetResolveCountReply].getName  // "sapi.EventsourcedViews$GetAppendCountReply"
  val ResolvedManifest: String             = classOf[Resolved].getName              // "sapi.EventsourcedViews$Resolved"
}

class ConditionalScalaSerializer extends SerializerWithStringManifest {
  import org.apache.commons.lang3.SerializationUtils
  import ConditionalScalaSerializer._

  /** Unique identifier for your Serializer.
    * 0 - 16 is reserved by Akka itself */
  def identifier: Int = 1317373

  /** The manifest (type hint) that will be provided in the fromBinary method.
    *  Use `""` if a manifest is not needed. */
  def manifest(obj: AnyRef): String =
    obj match {
      case GetAppendCount          => GetAppendCountManifest
      case GetResolveCount         => GetResolveCountManifest
      case _: GetResolveCountReply => GetResolveCountReplyManifest
      case _: Resolved             => ResolvedManifest
    }

  protected def longAsByteArray(long: Long): Array[Byte] = {
      val bb: ByteBuffer = java.nio.ByteBuffer.allocate(8) // Long occupies 8 bytes
      bb.putLong(long)
      bb.array
    }

  /** Serializes the given object to an Array[Byte] */
  def toBinary(obj: AnyRef): Array[Byte] =
    obj match {
      case GetAppendCount =>
        "".getBytes(UTF_8)  // case objects have no payload

      case GetResolveCount =>
        "".getBytes(UTF_8)  // case objects have no payload

      case GetResolveCountReply(count) =>
        longAsByteArray(count)

      case Resolved(selectedTimestamp) =>
        val x = selectedTimestamp.value
        val y = selectedTimestamp.value.asInstanceOf[Serializable]
        SerializationUtils.serialize(selectedTimestamp.value.asInstanceOf[Serializable])
    }

  /** Deserializes the given Array[Byte] using the type hint (`manifest`) */
  def fromBinary(bytes: Array[Byte], manifest: String): AnyRef =
    manifest match {
      case GetAppendCountManifest =>
        GetAppendCount

      case GetResolveCountManifest =>
        GetResolveCount

      case GetResolveCountReplyManifest =>
        GetResolveCountReply(ByteBuffer.wrap(bytes).getLong)

      case ResolvedManifest =>
        Resolved(SerializationUtils.deserialize(bytes))

      case _ => throw new java.io.NotSerializableException
    }
}
//#
