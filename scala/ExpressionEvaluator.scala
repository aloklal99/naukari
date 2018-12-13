abstract class Tree

class Sum(l: Tree, r: Tree) extends Tree
class Var(n: String) extends Tree
class Const(v: Int) extends Tree

object ExpressionEvaluator {
    
    type Environment = String => Int

    def main(args: Array[String]) {
        val expression = Sum(Const(-110), Sum(Var("x"), Var("y")))
        println(expression)

        val env = { 
            case "x" => 1
            case "y" => 10
        }
        println(env)

        result = eval(expression, env)
        println(s"result: $result")
    }

    def eval(t: Tree, env: Environment): Int = t match {
        case Const(v) => v
        case Var(n) => env(n)
        case Sum(l, r) => eval(l, env) + env(r, env)
    }
}