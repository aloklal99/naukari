trait Iterator[T] {
    def hasNext: Boolean
    def next(): T
}

class IntIterator(to: Int) extends Iterator[Int] {
    private var i = 0
    override def hasNext: Boolean = i < to
    override def next(): Int = {
        if (hasNext) {
            val returnVal = i
            i += 1
            returnVal
        } else {
            0
        }
    }
}

object IteratorTrait {
    def main(args: Array[String]): Unit = {
        val i1 = new IntIterator(to=5)
        while (i1.hasNext) {
            println(i1.next())
        }
    }
}