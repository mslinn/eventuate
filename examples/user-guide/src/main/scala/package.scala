import com.rbmhtechnology.eventuate.VectorTime
import org.apache.commons.lang3.SerializationUtils

package object sapi {
  object RichVectorTime {
    def deserialize(byteArray: Array[Byte]): VectorTime =
      VectorTime(SerializationUtils.deserialize[Map[String, Long]](byteArray))
  }

  implicit class RichVectorTime(vectorTime: VectorTime) {
    def serialize: Array[Byte] = SerializationUtils.serialize(vectorTime.value.asInstanceOf[Serializable])
  }
}

