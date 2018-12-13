import scala.collection.mutable.ArrayBuffer

trait Pet {
    val name: String
}

class Cat(val name: String) extends Pet

class Dog(val name: String) extends Pet

object Pets {
    def main(args: Array[String]): Unit = {
        val animals = ArrayBuffer.empty[Pet]
        animals.append(new Cat("Cat1"))
        animals.append(new Dog("Dog1"))

        animals.foreach((pet) => println(pet.name))
    }
}