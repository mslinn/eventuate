import com.rbmhtechnology.eventuate.VectorTime
import org.apache.commons.lang3.SerializationUtils
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest._
import org.scalatest.Matchers._

@RunWith(classOf[JUnitRunner])
class TestVectorTimeSerialization extends WordSpec {
  "VectorTime" should {
    "manually serialize and deserialize" in {
      val desired = VectorTime("a" -> 1L, "b" -> 2L, "c" -> 3L)
      val byteArray: Array[Byte] = SerializationUtils.serialize(desired.value.asInstanceOf[Serializable])
      val map: Map[String, Long] = SerializationUtils.deserialize(byteArray)
      val actual = VectorTime(map.toSeq: _*)
      actual === desired
    }

    "implicitly serialize and deserialize" in {
      import sapi._
      val desired = VectorTime("a" -> 1L, "b" -> 2L, "c" -> 3L)
      val byteArray: Array[Byte] = desired.serialize
      val actual: VectorTime = RichVectorTime.deserialize(byteArray)
      actual === desired
    }
  }
}
