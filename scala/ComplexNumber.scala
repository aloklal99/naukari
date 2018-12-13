object ComplexNumber {
    def main(args: Array[String]) {
        val c1 = new Complex1(1.2, 3.4)
        println("Imaginary part: " + c1.im())
        println(c1)

        val c2 = new Complex(5.6, 7.8)
        println("Imaginary part: " + c2.im)
        println(c2)
    }
}