import com.rbmhtechnology.eventuate.{VectorTime, Versioned}
import org.apache.commons.lang3.SerializationUtils

// todo suggest a PR that would fold this into VectorTime and Versioned. Surely everyone would want this
package object sapi {
  // Requires "org.apache.commons" % "commons-lang3"
  object RichVectorTime {
    def deserialize(byteArray: Array[Byte]): VectorTime =
      VectorTime(SerializationUtils.deserialize[Map[String, Long]](byteArray))
  }

  implicit class RichVectorTime(vectorTime: VectorTime) {
    def serialize: Array[Byte] = SerializationUtils.serialize(vectorTime.value.asInstanceOf[Serializable])
  }

  object RichVersioned {
    def deserialize[A](a: Array[Byte]): Versioned[A] = SerializationUtils.deserialize(a)
  }

  implicit class RichVersioned[A](versioned: Versioned[A]) {
    def serialize: Array[Byte] = SerializationUtils.serialize(versioned.asInstanceOf[Serializable])
  }
}

