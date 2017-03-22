import com.rbmhtechnology.eventuate.VectorTime
import org.apache.commons.lang3.SerializationUtils

package object sapi {
  // todo suggest a PR that would fold this into VectorTime. Surely everyone would want this?
  // Requires "org.apache.commons" % "commons-lang3"
  object RichVectorTime {
    def deserialize(byteArray: Array[Byte]): VectorTime =
      VectorTime(SerializationUtils.deserialize[Map[String, Long]](byteArray))
  }

  implicit class RichVectorTime(vectorTime: VectorTime) {
    def serialize: Array[Byte] = SerializationUtils.serialize(vectorTime.value.asInstanceOf[Serializable])
  }
}

