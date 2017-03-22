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

//#event-driven-communication
package sapi

import akka.serialization._

sealed trait PPBase
case class Ping(num: Int) extends PPBase
case class Pong(num: Int) extends PPBase

object PingPongScalaSerializer {
  val UTF_8: String = java.nio.charset.StandardCharsets.UTF_8.name

  val PingManifest: String = classOf[Ping].getName   // "sapi.Ping"
  val PongManifest: String = classOf[Pong].getName   // "sapi.Pong"
}

class PingPongScalaSerializer extends SerializerWithStringManifest {
  import java.nio.ByteBuffer
  import PingPongScalaSerializer._

  /** Unique identifier for your Serializer.
    * 0 - 16 is reserved by Akka itself */
  def identifier: Int = 93478411

  /** The manifest (type hint) that will be provided in the fromBinary method.
    *  Use `""` if a manifest is not needed. */
  def manifest(obj: AnyRef): String =
    obj match {
      case _: Ping => PingManifest
      case _: Pong => PongManifest
    }

  protected def intAsByteArray(int: Int): Array[Byte] = {
    val bb: ByteBuffer = java.nio.ByteBuffer.allocate(4) // Int occupies 4 bytes
    bb.putInt(int)
    val x = bb.array
    x
  }

  /** Serializes the given object to an Array[Byte] */
  def toBinary(obj: AnyRef): Array[Byte] =
    obj match {
      case Ping(num) =>
        intAsByteArray(num)

      case Pong(num) =>
        intAsByteArray(num)
    }

  /** Deserializes the given Array[Byte] using the type hint (`manifest`) */
  def fromBinary(bytes: Array[Byte], manifest: String): AnyRef =
    manifest match {
      case PingManifest =>
        Ping(ByteBuffer.wrap(bytes).getInt)

      case PongManifest =>
        Pong(ByteBuffer.wrap(bytes).getInt)

      case _ => throw new java.io.NotSerializableException
    }
}
//#
