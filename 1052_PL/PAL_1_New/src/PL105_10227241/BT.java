package PL105_10227241;

import java.util.Scanner;

// Functions of Build Tree and Print Tree
class BT {
  
  // 跑主要三種文法開頭的遞迴
  // 1. ATOM
  // 2. LP <S-Exp> {<S-Exp>} [DOT <S-Exp>] RP
  // 3. QUOTE <S-Exp>
  static Node ReadSExp( Scanner oReader ) throws MyException {
    Token t = GT.GetToken( oReader ) ;


    if ( t != null ) {
      t.Classify() ;
      
      if ( IsATOM( t ) ) {
        return new Node( t ) ;
      } // if
      
      // token若是左括號, 就開新節點
      // 左子樹呼叫自己(ReadSExp)去跑, 右子樹呼叫BLPT去跑左括號文法
      else if ( t.GetIntType() == Type.LP ) {
        Node node = new Node() ;
        node.SetLeft( ReadSExp( oReader ) ) ;
        Token nu = null ;           // 風格檢查要求的
        node.SetRight( BuildLPTree( oReader, nu, false ) ) ;
        return node ;
      } // else if
      
      // token若是quote, 就新開一個節點
      // 節點的左子樹塞quote, 右子樹再開一層節點
      // 右子樹節點的左邊去呼叫自己去跑遞迴, 右邊為nil(null)
      else if ( t.GetIntType() == Type.QUOTE ) {
        Node node = new Node() ;
        node.SetLeft( new Node( t ) ) ;
        Node node_2 = new Node() ;
        node_2.SetLeft( ReadSExp( oReader ) ) ;
        node.SetRight( node_2 ) ;
        return node ;
      } // else if
      
      
      // 非atom/左括號/quote此三種token, 即丟出error msg
      else {
        throw new MyException( 
                               "ERROR (unexpected token) : atom or '(' expected when token at Line " +
                               t.GetLine() + " Column " + t.GetColumn() + " is >>" + 
                               t.PrintToken() + "<<\n", 4 ) ;
      } // else
    } // if
    
    return null ;
  } // ReadSExp()
  
  
  // --------------------------------------------------------------------------
  
  
  // 跑左括號文法的遞迴
  // LP <S-Exp> {<S-Exp>} [DOT <S-Exp>] RP
  static Node BuildLPTree( Scanner oReader, Token temp_t, boolean UseTempToken ) throws MyException {
    Token t ;
    // 如果沒有之前get到卻沒用到的token, 就get一個新token, 否則用那個還沒用到的token
    if ( ! UseTempToken ) t = GT.GetToken( oReader ) ;
    else t = temp_t ;
    
    if ( t != null ) {
      t.Classify() ;
      
      // token是右括號的話, 則回傳null, 表示結束
      if ( t.GetIntType() == Type.RP ) {
        return null ;
      } // if
      
      // token是左括號的話, 開新節點, 然後再get一個新的token
      // 若非atom/左括號/quote則丟error msg, 如果是atom/左括號/quote就把新get到的token丟進BLPT去遞迴建左子樹
      // 等於節點的左子樹跟右子樹都是跑後續token的文法(依照左括號開頭的文法), 右子樹等左子樹建完再建
      // 會碰到的case: ( (123) (456) )
      //                    ^^^^^
      else if ( t.GetIntType() == Type.LP ) {
        Node node = new Node() ;
        t = GT.GetToken( oReader ) ;
        t.Classify() ;
        if ( ! IsATOM( t ) && t.GetIntType() != Type.LP &&
             t.GetIntType() != Type.QUOTE  ) 
          throw new MyException( 
                                 "ERROR (unexpected token) : atom or '(' expected when token at Line " +
                                 t.GetLine() + " Column " + t.GetColumn() + " is >>" + 
                                 t.PrintToken() + "<<\n", 4 ) ;
        else node.SetLeft( BuildLPTree( oReader, t, true ) ) ;
        Token nu = null ;           // 風格檢查要求的
        node.SetRight( BuildLPTree( oReader, nu, false ) ) ;    
        return node ;
      } // else if
      
      
      // token是quote的話, 先開新的節點(node), 然後node的左邊塞node2
      // node2的左邊是quote, 右邊塞node3, node3左邊是跟在quote後面的東西, 右邊是nil(null)
      // node的右邊就是等quote後面的東西建完後, 再看後面的token串(maybe是右括號或其他東西)
      // 最後回傳整個建好的tree的root ----> node
      // 會碰到的case: ( 122 '78 )
      else if ( t.GetIntType() == Type.QUOTE ) {
        Node node = new Node() ;
        Node node_2 = new Node() ;
        Node node_3 = new Node() ;
        node_2.SetLeft( new Node( t ) ) ;
        node_3.SetLeft( ReadSExp( oReader ) ) ;
        node_2.SetRight( node_3 ) ;
        node.SetLeft( node_2 ) ;
        Token nu = null ;           // 風格檢查要求的
        node.SetRight( BuildLPTree( oReader, nu, false ) ) ;
        return node ;
      } // else if
      
      
      // token是atom的話, 在左括號文法中, 要多開一層節點
      // 然後節點的左邊塞此atom node, 右邊跑後續token串(依照左括號 文法)
      else if ( IsATOM( t ) ) {
        Node node = new Node() ;
        node.SetLeft( new Node( t ) ) ;
        Token nu = null ;           // 風格檢查要求的
        node.SetRight( BuildLPTree( oReader, nu, false ) ) ;
        return node ;
      } // else if
      
      
      // token是dot的話, 就新開節點然後跑後續token串(依照大架構文法:atom/左括號/quote開頭)
      // 然後遞迴回來, get下一個token看是否為RP, 照左括號文法來說, dot後面接一個S-exp, 再之後一定要接右括號
      // 若非右括號就丟error msg, 是的話就回傳此node ( 變成其他node的右邊節點 )
      else if ( t.GetIntType() == Type.DOT ) {
        Node node = ReadSExp( oReader ) ;
        
        t = GT.GetToken( oReader ) ;
        t.Classify() ;
        if ( t.GetIntType() != Type.RP ) {
          throw new MyException( 
                                 "ERROR (unexpected token) : ')' expected when token at Line " +
                                 t.GetLine() + " Column " + t.GetColumn() + " is >>" + 
                                 t.PrintToken() + "<<\n", 5 ) ;
        } // if
        else return node ;
      } // else if
      
      
      // 非atom/左括號/quote此三種token, 即丟出error msg
      else {
        throw new MyException( 
                               "ERROR (unexpected token) : atom or '(' expected when token at Line " +
                               t.GetLine() + " Column " + t.GetColumn() + " is >>" + 
                               t.PrintToken() + "<<\n", 4 ) ;
      } // else
    } // if
    
    
    // t (token) == null  
    else {
      throw new MyException( 
                             "ERROR (unexpected token) : atom or '(' expected when token at Line " +
                             t.GetLine() + " Column " + t.GetColumn() + " is >>" + 
                             t.PrintToken() + "<<\n", 4 ) ;
    } // else
    
  } // BuildLPTree()
  
  
  // --------------------------------------------------------------------------
  
  
  // ====================== String_用來PrintTree的Function ======================
  
  // 印空白
  static String PrintSpace( int num, String str ) {
    for ( int i = 0 ; i < num ; i ++ )
      str = str + " ";
    
    return str ;
  } // PrintSpace()
  
  
  // 印左子樹
  // 第一層call的時候,參數為BT.PrintLTree( Node名稱, 0, false, "" )
  // num 為要印的空白的數量, str為前面所存取的字串
  // 如果是ATOM就印空白+Token
  // 如果是樹就看下一個要印的東西是不是子樹的第一層
  // 如果是第一層,printSpace為False,即直接印左括號不要印空白
  // 如果不是,printSpace為True,即要印空白再印左括號
  // 印完左邊跟右邊的東西後,要印空白+右括號
  static String PrintLTree( Node node, int num, boolean printSpace, String str ) {
    if ( node.GetToken() != null ) {
      if ( printSpace ) str = PrintSpace( num, str ) ;
      str = str + node.GetToken().PrintToken() + "\n";
    } // if
    
    else {
      // 
      if ( printSpace ) str = PrintSpace( num, str ) ;
      str = str + "( " ;
      str = PrintLTree( node.GetLeft(), num+2, false, str ) ;
      str = PrintRTree( node.GetRight(), num+2, str ) ;
      str = PrintSpace( num, str ) ;
      str = str + ")" + "\n" ;
    } // else
    
    return str ;
  } // PrintLTree()
  
  
  // 印右子樹
  // 如果節點為null即return,不要做任何動作
  // 如果是nil的話,也是return,不要印   (右邊節點為nil就不要印)
  // 如果是nil之外的ATOM就印空白+點,換行再印空白+Token
  // 如果是樹就呼叫印左子樹的Function去印左邊,再呼叫印右子樹的Function去印右邊
  static String PrintRTree( Node node, int num, String str ) {
    if ( node == null ) return str ;
    if ( node.GetToken() != null && node.GetToken().GetIntType() == Type.NIL ) return str ;
    else if ( node.GetToken() != null ) {
      str = PrintSpace( num, str ) ;
      str = str + "." + "\n" ;
      str = PrintSpace( num, str ) ;
      str = str + node.GetToken().PrintToken() + "\n" ;
    } // else if
    
    else {
      str = PrintLTree( node.GetLeft(), num, true, str ) ;
      str = PrintRTree( node.GetRight(), num, str ) ;
    } // else
    
    return str ;
  } // PrintRTree()
  
  // ====================== String_用來PrintTree的Function ======================
  
  
  
  // 判斷是否為ATOM
  static boolean IsATOM( Token t ) {
    int type = t.GetIntType() ;
    if ( type == Type.INT || type == Type.FLOAT || type == Type.STRING ||
         type == Type.SYMBOL || type == Type.NIL || type == Type.T ) {
      return true ;
    } // if
    else return false ;
  } // IsATOM()
  
} // class BT
