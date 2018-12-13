class Point(var x: Int, var y: Int) {
    def move(by: Point) {
        x += by.x
        y += by.y
    }
    override def toString(): String = {
        return s"($x, $y)"
    }
}

class Point2 {
    private var x_ = 0
    private var y_ = 0
    def x = x_
    def x_ = (z: Int): Unit = {
         x_ = z 
    }
    // def x_= (newValue: Int): Unit = {
    //     x_ = newValue
    // }
    override def toString() = s"($x_, $y_)"
}
object PointMain {
    def main(args: Array[String]): Unit = {
        val p = new Point(3, 4)
        val by = new Point(0, 1)
        p.move(by)
        println(p)

        val p2 = new Point2
        println(p2)
        p2.x = 30
        println(p2)
        
    }
}