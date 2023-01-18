

/**
 * pas utilisé
*/

package linkedList
class LinkedList {
  var nodes: List[Node] = List()
  var head: Node = null
  var tail: Node = null

  /** Déplace chaque noeud dans la case précédente
    */
  def shift(xCoord: Int, yCoord: Int): Unit = {

    try {
// shift all nodes
      var newnodesList: List[Node] = List()
      val newHead = new Node()

      newHead.xCoord = xCoord
      newHead.yCoord = yCoord
      newHead.next = null
      head = newHead
      newnodesList = newHead :: newnodesList
      var len = if(nodes.length > 1) 2 else 1
      if (nodes.length >= 1) {
        for (i <- 0 to nodes.length - len) {
          newnodesList = nodes(i) :: newnodesList
          if(i == nodes.length-len)
          {
            tail = nodes(i)
          }
        }
      } else {

      }
      nodes = newnodesList

    } catch {
      case e: Exception => println("Exception caught: " + e);
    }

  }

  /** Ajoute un noeud dans la liste
    */
  def addNode(xCoord: Int, yCoord: Int): Unit = {
    // Ajouter un noeud à la fin du serpent
    val newNode = new Node()
    newNode.xCoord = xCoord
    newNode.yCoord = yCoord
    if (tail != null) {
      tail.next = newNode
    } else {
      tail = newNode
      head = newNode
    }
    nodes = newNode :: nodes
  }
}

class Node {
  var xCoord: Int = 0
  var yCoord: Int = 0
  var next: Node = null
}
