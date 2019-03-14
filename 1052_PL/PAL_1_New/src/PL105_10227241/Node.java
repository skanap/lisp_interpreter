package PL105_10227241;

class Node {
  private Token mToken ;
  private Node mLeft ;
  private Node mRight ;
  
  // Constructor
  Node() {
    mToken = null ;
    mLeft = null ;
    mRight = null ;
  } // Node()
  
  Node( Token token ) {
    this.mToken = token ;
    mLeft = null ;
    mRight = null ;
  } // Node()
  
  
  boolean IsNULL() {
    if ( mToken == null && mLeft == null && mRight == null ) return true ;
    else return false ;
  } // IsNULL()
  
  void SetToken( Token t ) {
    this.mToken = t ;
  } // SetToken()

  Token GetToken() {
    return mToken ;
  } // GetToken()
  
  void SetLeft( Node left ) {
    this.mLeft = left ;
  } // SetLeft()
  
  void SetRight( Node right ) {
    this.mRight = right ;
  } // SetRight()
  
  Node GetLeft() {
    return mLeft ;
  } // GetLeft()
  
  Node GetRight() {
    return mRight ;
  } // GetRight()
  
} // class Node
