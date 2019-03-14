package PL105_10227241;

import java.util.Scanner;

// Functions of Build Tree and Print Tree
class BT {
  
  // �]�D�n�T�ؤ�k�}�Y�����j
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
      
      // token�Y�O���A��, �N�}�s�`�I
      // ���l��I�s�ۤv(ReadSExp)�h�], �k�l��I�sBLPT�h�]���A����k
      else if ( t.GetIntType() == Type.LP ) {
        Node node = new Node() ;
        node.SetLeft( ReadSExp( oReader ) ) ;
        Token nu = null ;           // �����ˬd�n�D��
        node.SetRight( BuildLPTree( oReader, nu, false ) ) ;
        return node ;
      } // else if
      
      // token�Y�Oquote, �N�s�}�@�Ӹ`�I
      // �`�I�����l���quote, �k�l��A�}�@�h�`�I
      // �k�l��`�I������h�I�s�ۤv�h�]���j, �k�䬰nil(null)
      else if ( t.GetIntType() == Type.QUOTE ) {
        Node node = new Node() ;
        node.SetLeft( new Node( t ) ) ;
        Node node_2 = new Node() ;
        node_2.SetLeft( ReadSExp( oReader ) ) ;
        node.SetRight( node_2 ) ;
        return node ;
      } // else if
      
      
      // �Datom/���A��/quote���T��token, �Y��Xerror msg
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
  
  
  // �]���A����k�����j
  // LP <S-Exp> {<S-Exp>} [DOT <S-Exp>] RP
  static Node BuildLPTree( Scanner oReader, Token temp_t, boolean UseTempToken ) throws MyException {
    Token t ;
    // �p�G�S�����eget��o�S�Ψ쪺token, �Nget�@�ӷstoken, �_�h�Ψ����٨S�Ψ쪺token
    if ( ! UseTempToken ) t = GT.GetToken( oReader ) ;
    else t = temp_t ;
    
    if ( t != null ) {
      t.Classify() ;
      
      // token�O�k�A������, �h�^��null, ��ܵ���
      if ( t.GetIntType() == Type.RP ) {
        return null ;
      } // if
      
      // token�O���A������, �}�s�`�I, �M��Aget�@�ӷs��token
      // �Y�Datom/���A��/quote�h��error msg, �p�G�Oatom/���A��/quote�N��sget�쪺token��iBLPT�h���j�إ��l��
      // ����`�I�����l���k�l�𳣬O�]����token����k(�̷ӥ��A���}�Y����k), �k�l�𵥥��l��ا��A��
      // �|�I�쪺case: ( (123) (456) )
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
        Token nu = null ;           // �����ˬd�n�D��
        node.SetRight( BuildLPTree( oReader, nu, false ) ) ;    
        return node ;
      } // else if
      
      
      // token�Oquote����, ���}�s���`�I(node), �M��node�������node2
      // node2������Oquote, �k���node3, node3����O��bquote�᭱���F��, �k��Onil(null)
      // node���k��N�O��quote�᭱���F��ا���, �A�ݫ᭱��token��(maybe�O�k�A���Ψ�L�F��)
      // �̫�^�Ǿ�ӫئn��tree��root ----> node
      // �|�I�쪺case: ( 122 '78 )
      else if ( t.GetIntType() == Type.QUOTE ) {
        Node node = new Node() ;
        Node node_2 = new Node() ;
        Node node_3 = new Node() ;
        node_2.SetLeft( new Node( t ) ) ;
        node_3.SetLeft( ReadSExp( oReader ) ) ;
        node_2.SetRight( node_3 ) ;
        node.SetLeft( node_2 ) ;
        Token nu = null ;           // �����ˬd�n�D��
        node.SetRight( BuildLPTree( oReader, nu, false ) ) ;
        return node ;
      } // else if
      
      
      // token�Oatom����, �b���A����k��, �n�h�}�@�h�`�I
      // �M��`�I������릹atom node, �k��]����token��(�̷ӥ��A�� ��k)
      else if ( IsATOM( t ) ) {
        Node node = new Node() ;
        node.SetLeft( new Node( t ) ) ;
        Token nu = null ;           // �����ˬd�n�D��
        node.SetRight( BuildLPTree( oReader, nu, false ) ) ;
        return node ;
      } // else if
      
      
      // token�Odot����, �N�s�}�`�I�M��]����token��(�̷Ӥj�[�c��k:atom/���A��/quote�}�Y)
      // �M�Ỽ�j�^��, get�U�@��token�ݬO�_��RP, �ӥ��A����k�ӻ�, dot�᭱���@��S-exp, �A����@�w�n���k�A��
      // �Y�D�k�A���N��error msg, �O���ܴN�^�Ǧ�node ( �ܦ���Lnode���k��`�I )
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
      
      
      // �Datom/���A��/quote���T��token, �Y��Xerror msg
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
  
  
  // ====================== String_�Ψ�PrintTree��Function ======================
  
  // �L�ť�
  static String PrintSpace( int num, String str ) {
    for ( int i = 0 ; i < num ; i ++ )
      str = str + " ";
    
    return str ;
  } // PrintSpace()
  
  
  // �L���l��
  // �Ĥ@�hcall���ɭ�,�ѼƬ�BT.PrintLTree( Node�W��, 0, false, "" )
  // num ���n�L���ťժ��ƶq, str���e���Ҧs�����r��
  // �p�G�OATOM�N�L�ť�+Token
  // �p�G�O��N�ݤU�@�ӭn�L���F��O���O�l�𪺲Ĥ@�h
  // �p�G�O�Ĥ@�h,printSpace��False,�Y�����L���A�����n�L�ť�
  // �p�G���O,printSpace��True,�Y�n�L�ťզA�L���A��
  // �L�������k�䪺�F���,�n�L�ť�+�k�A��
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
  
  
  // �L�k�l��
  // �p�G�`�I��null�Yreturn,���n������ʧ@
  // �p�G�Onil����,�]�Oreturn,���n�L   (�k��`�I��nil�N���n�L)
  // �p�G�Onil���~��ATOM�N�L�ť�+�I,����A�L�ť�+Token
  // �p�G�O��N�I�s�L���l��Function�h�L����,�A�I�s�L�k�l��Function�h�L�k��
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
  
  // ====================== String_�Ψ�PrintTree��Function ======================
  
  
  
  // �P�_�O�_��ATOM
  static boolean IsATOM( Token t ) {
    int type = t.GetIntType() ;
    if ( type == Type.INT || type == Type.FLOAT || type == Type.STRING ||
         type == Type.SYMBOL || type == Type.NIL || type == Type.T ) {
      return true ;
    } // if
    else return false ;
  } // IsATOM()
  
} // class BT
