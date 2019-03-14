package PL105_10227241;

import java.util.Vector;

class MyFunctions {
  
  // -------------------------------------------------------------------------------
  // Function Name : cons
  // ���ӰѼƵ��X���@��dotted pair
  static Node Cons( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    ans.SetLeft( vec.get( 0 ) );
    ans.SetRight( vec.get( 1 ) );
    return ans ;
  } // Cons()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : list
  // ��Ҧ��ѼƵ��X���@��list
  static Node List( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    Node head = new Node() ;
    ans = head ;
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      head.SetLeft( vec.get( i ) );
      Node temp = new Node() ;
      head.SetRight( temp ) ;
      if ( i != vec.size()-1 ) head = head.GetRight() ;  // �p�G�O�̫�@�ӰѼƴN���n�b���U�@�h�]
    } // for
    
    Node nu = null ;           // �����ˬd�n�D��
    head.SetRight( nu ) ;  // ��̫�@�h���k�`�I�]��null
    
    // �ѼƼƶq��0, �Y�^��nil
    if ( vec.size() == 0 ) {
      Token temp = new Token( "nil", 0, 0 ) ;
      temp.Classify() ;
      ans.SetToken( temp ) ;
    } // if
    
    return ans ;
  } // List()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : define
  // �w�q�Ѽƪ��N�q, parameter1���w�q��parameter2
  static void Define( Node node ) throws MyException {
    // _______________________________ �ܼƫŧi�� _______________________________
    
    Node para1 = node.GetLeft() ;              // �Ѽ�1, �Q�w�q��symbol
    Node para2 = node.GetRight().GetLeft() ;   // �Ѽ�2, Binding (value)
                                                                                
    DefObject def1 = null, def2 = null, new_def = null ;                        
    Boolean para1_IsDefined = false ;      // �Ѽ�1���S���Q�w�q�L (�YsVec_Def�s���s�b�Ѽ�1)
    
    int para_num = 0 ;                                    // define function ��
    Vector<DefObject> f_para = new Vector<DefObject>() ;  // define function ��
    Vector<Node> f_body = new Vector<Node>() ;            // define function ��
    
    // _______________________________ �ܼƫŧi�� _______________________________
    
    
    // _______________________________ �B�z�Ѽ�1 _______________________________
    
    // �Ѽ�1�OSymbol
    if ( para1.GetToken() != null && para1.GetToken().GetIntType() == Type.SYMBOL ) {
      // �p�G�n�w�q�@��symbol��binding, define�᭱�u������ӰѼ�(symbol name & binding)
      // �Y����ӥH�W���ѼƧY��Xerror msg
      if ( node.GetRight().GetRight() != null && node.GetRight().GetRight().GetToken() == null
           && node.GetRight().GetRight().GetLeft() != null )
        throw new MyException( "", 87 ) ;

      def1 = Evaluate.FindDefine( para1, true ) ;    // �M��O�_�Q�w�q�L, �W�r��Binding�۵�
    } // if


    // �Ѽ�1�OList, �Y���ŦX�۩w�qFunction���榡
    // (define ( ... ) ......) // �w�qfunction
    // =================================================================
    else if ( Evaluate.ParameterType( para1 ).equals( "list" ) ) {
      if ( para1.GetLeft().GetToken().GetIntType() != Type.SYMBOL ) 
        throw new MyException( "", 87 ) ;
      
      else {
        // ���o function name
        String f_name = para1.GetLeft().GetToken().PrintToken() ;
        DefObject def_func = Evaluate.FindDefine( new Node( new Token( f_name, 0, 0 ) ),
                                                  true ) ;
        // �O�t�Τ���function, �G����Q���Ʃw�q -> ��error msg
        if ( def_func != null && def_func.mType.equals( "function" ) )
          throw new MyException( "", 87 ) ;
        
        // �Q�w�q�L
        else if ( def_func != null ) {
          Main.sVec_Def.remove( def_func ) ;   // para1_IsDefined = true ;
        } // else if
        
        // �S�Q�w�q�L
        else ;
        
        
        // ------ ���o ( function_name  { function_para } ) ------ 
        para1 = para1.GetRight() ;
        while ( para1 != null ) {
          if ( para1.GetToken() != null && para1.GetToken().GetIntType() == Type.NIL ) ;
          
          // �k�`�I�O��ATOM NODE
          else if ( para1.GetToken() != null ) throw new MyException( "", 87 ) ;
          
          else if ( para1.GetLeft() != null && para1.GetLeft().GetToken() != null 
                    && para1.GetLeft().GetToken().GetIntType() != Type.SYMBOL )
            throw new MyException( "", 87 ) ;
          
          else {
            para_num ++ ;
            Node nu = null ;
            DefObject p = new DefObject( para1.GetLeft().GetToken().PrintToken(), 
                                         "symbol", nu ) ;
            f_para.add( p ) ;
          } // else
          
          para1 = para1.GetRight() ;
        } // while
        // ------ ���o ( function_name  { function_para } ) ------
        
        
        // ------------------ ���o function body ------------------
        Node para_f_body = node.GetRight() ;
        
        while ( para_f_body != null ) {
          if ( ! Evaluate.IsPureList( para_f_body.GetLeft() ) ) throw new MyException( "", 87 ) ;
          
          if ( para_f_body.GetToken() != null && para_f_body.GetToken().GetIntType() == Type.NIL ) ;
          
          // �k�`�I�O��ATOM NODE
          else if ( para_f_body.GetToken() != null ) throw new MyException( "ERROR (non-list) : ", 11 ) ;
          
          else f_body.add( para_f_body.GetLeft() ) ;
          
          para_f_body = para_f_body.GetRight() ;
        } // while
        // ------------------ ���o function body ------------------
        
        
        new_def = new DefObject( f_name, "function_lambda",
                                           new Node( new Token( "#<procedure " + f_name  + ">", 0, 0 ) ),
                                           para_num, "s-exp", f_para, f_body ) ;
        
        Main.sVec_Def.add( new_def ) ;
        
        if ( Main.sIsVerbose )     // verbose mode�}��, �~�n�L�@�Ӵ���
          System.out.println( f_name + " defined" );
      } // else
      
      return ;
    } // else if �Ѽ�1 �O list, �Y���ŦX�۩w�qFunction���榡
    // =================================================================

    // define format error
    else throw new MyException( "", 87 ) ;
    
    
    // ------------------- �ݰѼ�1���S���Q�w�q�L -------------------
    // �Q�w�q�L�B�D����function
    if ( def1 != null && ! def1.GetType().equals( "function" ) ) {
      para1_IsDefined = true ;
      // ���O�ۦ��Q�w�q�L,�٤���remove��,�]���p�G�Ѽ�2���Ψ�쥻���w�q�N�䤣��쥻���w�q�F
      // Main.sVec_Def.remove( def1 ) ;
    } // if

    // �Q�w�q�L�B������function
    else if ( def1 != null && def1.GetType().equals( "function" ) )
      // �Ѽ�1���t�Τ���Function
      throw new MyException( "", 87 ) ;
    
    // def1 == null
    else ;
    // ------------------- �ݰѼ�1���S���Q�w�q�L -------------------
    
    // _______________________________ �B�z�Ѽ�1 _______________________________
    
    
    // _______________________________ �B�z�Ѽ�2 _______________________________
    
    Node para2_eval = new Node() ;
    try {
      para2_eval = Evaluate.EvalSExp( para2, 1 ) ;
    } catch ( MyException e ) {
      // �n�w�q��binding evaluate�� No return value -> error
      if ( e.GetCase() == 88 ) 
        throw new MyException( "ERROR (no return value) : " + 
                               BT.PrintLTree( para2, 0, false, "" ), 23 ) ;
      else throw e ;
    } // catch
    
    if ( para2_eval.GetToken() != null ) {
      // System.out.println(para2_eval.GetToken().PrintToken() + para2_eval.GetToken().GetStringType() ) ;
      def2 = Evaluate.FindDefine( para2_eval, false ) ;
      // ���F�B�z (define x (quote a)) , �ҥHFindDefine�Ѽƶ�false
      // �nbinding��token�@�ˤ~��Q�w�q�L, �W�r��token�@�� ���Υh�䥦��binding
      // �]�� 'a �N�� 'cons �@��, �N�� cons �ݰ_�ӬO���w�q�L, ���ƹ�W���O binding �� #<procedure cons>
    } // if
      
    // �o�ɭԦA�R���쥻���w�q, �]���Ѽ�2�w�Qevaluate
    if ( para1_IsDefined ) Main.sVec_Def.remove( def1 ) ;
    
    // �Ѽ�2�O���Fsymbol�~��atom node (int,float,string,#t,nil)
    // ���ݭn��def2���S�����, ���M (define a 5 ) (define b a ) �o��case def2���Dnull
    // ����binding
    if ( para2_eval.GetToken() != null && Evaluate.IsATOM( para2_eval.GetToken() ) ) {
      new_def = new DefObject( para1.GetToken().PrintToken(), "symbol", para2_eval ) ;
    } // if
    
    
    // Define + Lambda
    else if ( para2_eval.GetToken() != null &&
              para2_eval.GetToken().PrintToken().equals( "#<procedure lambda>" ) ) {
      
      // (define x (lambda ( a b ) ( + a 5 ) ( + b 5 )))
      if ( def2 == null ) {
        new_def = Main.sLambda_Temp ;
        new_def.SetName( para1.GetToken().PrintToken() ) ;
      } // if
      
      // (define x (lambda ( a b ) ( + a 5 ) ( + b 5 )))
      // (define y x )
      else {
        new_def = new DefObject( para1.GetToken().PrintToken(), def2.GetType(), def2.GetBinding(), 
                                 def2.GetParameterNumber(), def2.GetParameterType(), 
                                 def2.GetFunctionParameter(), def2.GetFunctionBody() ) ;
      } // else  
    } // else if
    
    // �Ѽ�2�O�g�Lquote�B�z�L��symbol atom node
    // ex : (define a 'hi)
    else if ( para2_eval.GetToken() != null && def2 == null
              && para2_eval.GetToken().GetIntType() == Type.SYMBOL )
      new_def = new DefObject( para1.GetToken().PrintToken(), "symbol", para2_eval ) ;
    
    // �Ѽ�2�O�DATOM Node �B �S�Q�w�q�L  ( �p : (1 . 2) )
    // ����binding
    else if ( para2_eval.GetToken() == null && def2 == null )
      new_def = new DefObject( para1.GetToken().PrintToken(), "symbol", para2_eval ) ;
    
    // �Ѽ�2�O�Q�w�q�L��function
    // ����binding (defObj��type��function_user)
    else if ( def2 != null && ( def2.GetType().equals( "function" ) || 
                                def2.GetType().equals( "function_user" ) ) ) {
      new_def = new DefObject( para1.GetToken().PrintToken(), "function_user", 
                               def2.GetParameterNumber(), def2.GetParameterType() ) ;
      // System.out.println( def2.GetBinding().GetToken().PrintToken() ) ;
      new_def.SetBinding( def2.GetBinding() ) ;
    } // else if
    
    
    // �Ѽ�2�O�Q�w�q�L��function�B���ϥΪ̦ۤv�w�q��function
    // ����binding (defObj��type��function_lambda)
    else if ( def2 != null && def2.GetType().equals( "function_lambda" ) ) {
      new_def = new DefObject( para1.GetToken().PrintToken(),
                               "function_lambda", def2.GetBinding(), 
                               def2.GetParameterNumber(), def2.GetParameterType(),
                               def2.GetFunctionParameter(), def2.GetFunctionBody() ) ;
    } // else if
    
    
    // ERROR Object
    else if ( para2_eval.GetToken() != null && para2_eval.GetToken().GetIntType() == Type.ERROR ) {
      new_def = new DefObject( para1.GetToken().PrintToken(), "symbol", para2_eval ) ;
    } // if
    
    
    // ************************
    else throw new MyException( "No Binding", 2222 ) ;
    // ************************
    
    // _______________________________ �B�z�Ѽ�2 _______________________________
    
    
    // _______________________________ �B�z�^�ǭ�  _______________________________
    
    Main.sVec_Def.add( new_def ) ;
    
    if ( Main.sIsVerbose )      // verbose mode�}��, �~�n�L�@�Ӵ���
      System.out.println( para1.GetToken().PrintToken() + " defined" ) ;
    
    return ;
    // _______________________________ �B�z�^�ǭ�  _______________________________

  } // Define()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : car
  // ��List���Ĥ@��Element
  static Node Car( Vector<Node> vec ) throws MyException {
    if ( Evaluate.ParameterType( vec.get( 0 ) ).equals( "list" ) )
      return vec.get( 0 ).GetLeft() ;   // �s�iVector�ɤw�gEvaluate�L�F,�G�����AEvaluate
    else throw new MyException( "ERROR (car with incorrect argument type) : "
                                + BT.PrintLTree( vec.get( 0 ), 0, false, "" ), 7 ) ;
  } // Car()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : cdr
  // ��List���F�Ĥ@�ӥ~,�ѤU��Element
  static Node Cdr( Vector<Node> vec ) throws MyException {
    if ( Evaluate.ParameterType( vec.get( 0 ) ).equals( "list" ) ) {
      
      // �p�G�k��`�I��null, �h�⥦�אּ�@��nil��atom node
      if ( vec.get( 0 ).GetRight() == null ) {
        Token t = new Token( "nil", 0, 0 ) ;
        t.Classify() ;
        vec.get( 0 ).SetRight( new Node( t ) ) ;
      } // if
      
      return vec.get( 0 ).GetRight() ;
    } // if
    else throw new MyException( "ERROR (cdr with incorrect argument type) : "
                                + BT.PrintLTree( vec.get( 0 ), 0, false, "" ), 7 ) ;
  } // Cdr()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Clean-Environment
  // �M���ϥΪ̩w�q��Symbol��Function
  static void Clean_Environment() {
    Main.sVec_Def.clear() ;
    Main.sVec_Def = Evaluate.InitDefObject() ;
    if ( Main.sIsVerbose ) System.out.print( "environment cleaned" ) ;
    System.out.println() ;  // ����verbose mode�O�_�}��, ���n�h�L�@�Ӵ���
  } // Clean_Environment()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : +
  // return�Ҧ��Ѽƥ[�`�᪺���G
  static Node Add( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    int i_ans = 0 ;
    double f_ans = 0 ;
    boolean allInteger = true ;
    
    // �P�_�Ѽƫ��O
    // �Y���Ointeger�]���Ofloat, �N�n��XError Message
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      if ( Evaluate.ParameterType( vec.get( i ) ).equals( "integer" ) ) ;
      else if ( Evaluate.ParameterType( vec.get( i ) ).equals( "float" ) )
        allInteger = false ;
      // �D�Ʀr���O���Ѽ�
      else throw new MyException( "ERROR (+ with incorrect argument type) : "
                                  + BT.PrintLTree( vec.get( i ), 0, false, "" ), 7 ) ;
    } // for
    
    
    // �p�G�������ѼƳ��OInteger
    if ( allInteger ) {
      for ( int i = 0 ; i < vec.size() ; i++ ) {
        i_ans = i_ans + Integer.parseInt( vec.get( i ).GetToken().PrintToken() ) ;
      } // for
      
      Token t = new Token( Integer.toString( i_ans ), 0, 0 ) ;
      t.Classify() ;
      ans.SetToken( t ) ;
    } // if
    
    
    // �䤤�ܤ֦��@�ӰѼƬOFloat
    else {
      for ( int i = 0 ; i < vec.size() ; i++ ) {
        f_ans = f_ans + Double.parseDouble( vec.get( i ).GetToken().PrintToken() ) ;
      } // for
      
      Token t = new Token( Double.toString( f_ans ), 0, 0 ) ;
      t.Classify() ;
      ans.SetToken( t ) ;
    } // else
    
    return ans ;
  } // Add()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : -
  // return�Ҧ��ѼƱq����k�۴�᪺���G
  static Node Sub( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    int i_ans = 0 ;
    double f_ans = 0 ;
    boolean allInteger = true ;
    
    // �P�_�Ѽƫ��O
    // �Y���Ointeger�]���Ofloat, �N�n��XError Message
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      if ( Evaluate.ParameterType( vec.get( i ) ).equals( "integer" ) ) ;
      else if ( Evaluate.ParameterType( vec.get( i ) ).equals( "float" ) )
        allInteger = false ;
      // �D�Ʀr���O���Ѽ�
      else throw new MyException( "ERROR (- with incorrect argument type) : "
                                  + BT.PrintLTree( vec.get( i ), 0, false, "" ), 7 ) ;
    } // for
    
    
    // �p�G�������ѼƳ��OInteger
    if ( allInteger ) {
      // �Ĥ@�ӰѼƧ@����l��, �M�����᪺�Ѽ�
      i_ans = Integer.parseInt( vec.get( 0 ).GetToken().PrintToken() ) ;
      
      // **** i�q1�}�l
      for ( int i = 1 ; i < vec.size() ; i++ ) {
        i_ans = i_ans - Integer.parseInt( vec.get( i ).GetToken().PrintToken() ) ;
      } // for
      
      Token t = new Token( Integer.toString( i_ans ), 0, 0 ) ;
      t.Classify() ;
      ans.SetToken( t ) ;
    } // if
    
    
    // �䤤�ܤ֦��@�ӰѼƬOFloat
    else {
      // �Ĥ@�ӰѼƧ@����l��, �M�����᪺�Ѽ�
      f_ans = Double.parseDouble( vec.get( 0 ).GetToken().PrintToken() ) ;
      
      // **** i�q1�}�l
      for ( int i = 1 ; i < vec.size() ; i++ ) {
        f_ans = f_ans - Double.parseDouble( vec.get( i ).GetToken().PrintToken() ) ;
      } // for
      
      Token t = new Token( Double.toString( f_ans ), 0, 0 ) ;
      t.Classify() ;
      ans.SetToken( t ) ;
    } // else
    
    return ans ;
  } // Sub()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : *
  // return�Ҧ��ѼƱq����k�ۭ��᪺���G
  static Node Mult( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    int i_ans = 0 ;
    double f_ans = 0 ;
    boolean allInteger = true ;
    
    // �P�_�Ѽƫ��O
    // �Y���Ointeger�]���Ofloat, �N�n��XError Message
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      if ( Evaluate.ParameterType( vec.get( i ) ).equals( "integer" ) ) ;
      else if ( Evaluate.ParameterType( vec.get( i ) ).equals( "float" ) )
        allInteger = false ;
      // �D�Ʀr���O���Ѽ�
      else throw new MyException( "ERROR (* with incorrect argument type) : "
                                  + BT.PrintLTree( vec.get( i ), 0, false, "" ), 7 ) ;
    } // for
    
    
    // �p�G�������ѼƳ��OInteger
    if ( allInteger ) {
      // �Ĥ@�ӰѼƧ@����l��, �M�᭼�H���᪺�Ѽ�
      i_ans = Integer.parseInt( vec.get( 0 ).GetToken().PrintToken() ) ;
      
      // **** i�q1�}�l
      for ( int i = 1 ; i < vec.size() ; i++ ) {
        i_ans = i_ans * Integer.parseInt( vec.get( i ).GetToken().PrintToken() ) ;
      } // for
      
      Token t = new Token( Integer.toString( i_ans ), 0, 0 ) ;
      t.Classify() ;
      ans.SetToken( t ) ;
    } // if
    
    
    // �䤤�ܤ֦��@�ӰѼƬOFloat
    else {
      // �Ĥ@�ӰѼƧ@����l��, �M�᭼�H���᪺�Ѽ�
      f_ans = Double.parseDouble( vec.get( 0 ).GetToken().PrintToken() ) ;
      
      // **** i�q1�}�l
      for ( int i = 1 ; i < vec.size() ; i++ ) {
        f_ans = f_ans * Double.parseDouble( vec.get( i ).GetToken().PrintToken() ) ;
      } // for
      
      Token t = new Token( Double.toString( f_ans ), 0, 0 ) ;
      t.Classify() ;
      ans.SetToken( t ) ;
    } // else
    
    return ans ;
  } // Mult()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : /
  // return�Ҧ��ѼƱq����k�۰��᪺���G, �Y���Ƭ�0�h��XError Message
  static Node Div( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    int i_ans = 0 ;
    double f_ans = 0 ;
    boolean allInteger = true ;
    
    // �P�_�Ѽƫ��O
    // �Y���Ointeger�]���Ofloat, �N�n��XError Message
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      if ( Evaluate.ParameterType( vec.get( i ) ).equals( "integer" ) ) ;
      else if ( Evaluate.ParameterType( vec.get( i ) ).equals( "float" ) )
        allInteger = false ;
      // �D�Ʀr���O���Ѽ�
      else throw new MyException( "ERROR (/ with incorrect argument type) : "
                                  + BT.PrintLTree( vec.get( i ), 0, false, "" ), 7 ) ;
    } // for
    
    
    // �p�G�������ѼƳ��OInteger
    if ( allInteger ) {
      // �Ĥ@�ӰѼƧ@����l��, �M�ᰣ�H���᪺�Ѽ�
      i_ans = Integer.parseInt( vec.get( 0 ).GetToken().PrintToken() ) ;
      
      // **** i�q1�}�l
      // �ˬd����(�᭱���Ѽ�)�O�_��0, �Y��0�h��XError Message
      for ( int i = 1 ; i < vec.size() ; i++ ) {
        int i_par = Integer.parseInt( vec.get( i ).GetToken().PrintToken() ) ;
        if ( i_par == 0 ) throw new MyException( "ERROR (division by zero) : /\n", 13 ) ;
        else i_ans = i_ans / Integer.parseInt( vec.get( i ).GetToken().PrintToken() ) ;
      } // for
      
      Token t = new Token( Integer.toString( i_ans ), 0, 0 ) ;
      t.Classify() ;
      ans.SetToken( t ) ;
    } // if
    
    
    // �䤤�ܤ֦��@�ӰѼƬOFloat
    else {
      // �Ĥ@�ӰѼƧ@����l��, �M�ᰣ�H���᪺�Ѽ�
      f_ans = Double.parseDouble( vec.get( 0 ).GetToken().PrintToken() ) ;
      
      // **** i�q1�}�l
      // �ˬd����(�᭱���Ѽ�)�O�_��0, �Y��0�h��XError Message
      for ( int i = 1 ; i < vec.size() ; i++ ) {
        double f_par = Double.parseDouble( vec.get( i ).GetToken().PrintToken() ) ;
        if ( f_par == 0 ) throw new MyException( "ERROR (division by zero) : /\n", 13 ) ;
        else f_ans = f_ans / Double.parseDouble( vec.get( i ).GetToken().PrintToken() ) ;
      } // for
      
      Token t = new Token( Double.toString( f_ans ), 0, 0 ) ;
      t.Classify() ;
      ans.SetToken( t ) ;
    } // else
    
    return ans ;
  } // Div()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : >
  // �P�_�Ҧ��ѼƬO�_���j�󥦫᭱���Ѽ�, �O����return #t (true), �_�hreturn nil (false)
  static Node IsGreaterThan( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    int i_cmp = 0 ;        // �@���������ǭ�
    double f_cmp = 0 ;     // �@���������ǭ�
    boolean allInteger = true, allTrue = true ;
    
    // �P�_�Ѽƫ��O
    // �Y���Ointeger�]���Ofloat, �N�n��XError Message
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      if ( Evaluate.ParameterType( vec.get( i ) ).equals( "integer" ) ) ;
      else if ( Evaluate.ParameterType( vec.get( i ) ).equals( "float" ) )
        allInteger = false ;
      // �D�Ʀr���O���Ѽ�
      else throw new MyException( "ERROR (> with incorrect argument type) : "
                                  + BT.PrintLTree( vec.get( i ), 0, false, "" ), 7 ) ;
    } // for
    
    
    // �p�G�������ѼƳ��OInteger
    if ( allInteger ) {
      // �Ĥ@�ӰѼƧ@���@�}�l�������ǭ�, �M��P���᪺�ѼƤ��
      i_cmp = Integer.parseInt( vec.get( 0 ).GetToken().PrintToken() ) ;
      
      // **** i�q1�}�l
      for ( int i = 1 ; i < vec.size() && allTrue ; i++ ) {
        int i_par = Integer.parseInt( vec.get( i ).GetToken().PrintToken() ) ;
        if ( i_cmp > i_par ) i_cmp = i_par ;  // ����True, �ç��ǭȳ]���᭱�@�ӰѼ�
        else allTrue = false ;
      } // for
    } // if
    
    
    // �䤤�ܤ֦��@�ӰѼƬOFloat
    else {
      // �Ĥ@�ӰѼƧ@���@�}�l�������ǭ�, �M��P���᪺�ѼƤ��
      f_cmp = Double.parseDouble( vec.get( 0 ).GetToken().PrintToken() ) ;
      
      // **** i�q1�}�l
      for ( int i = 1 ; i < vec.size() && allTrue ; i++ ) {
        double f_par = Double.parseDouble( vec.get( i ).GetToken().PrintToken() ) ;
        if ( f_cmp > f_par ) f_cmp = f_par ;  // ����True, �ç��ǭȳ]���᭱�@�ӰѼ�
        else allTrue = false ;
      } // for
    } // else
    
    Token t ;
    if ( allTrue ) t = new Token( "#t", 0, 0 ) ;
    else t = new Token( "nil", 0, 0 ) ;
    t.Classify() ;
    ans.SetToken( t ) ;
    
    return ans ;
  } // IsGreaterThan()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : >=
  // �P�_�Ҧ��ѼƬO�_���j��ε��󥦫᭱���Ѽ�, �O����return #t (true), �_�hreturn nil (false)
  static Node IsGreaterThanOrEqual( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    int i_cmp = 0 ;       // �@���������ǭ�
    double f_cmp = 0 ;     // �@���������ǭ�
    boolean allInteger = true, allTrue = true ;
    
    // �P�_�Ѽƫ��O
    // �Y���Ointeger�]���Ofloat, �N�n��XError Message
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      if ( Evaluate.ParameterType( vec.get( i ) ).equals( "integer" ) ) ;
      else if ( Evaluate.ParameterType( vec.get( i ) ).equals( "float" ) )
        allInteger = false ;
      // �D�Ʀr���O���Ѽ�
      else throw new MyException( "ERROR (>= with incorrect argument type) : "
                                  + BT.PrintLTree( vec.get( i ), 0, false, "" ), 7 ) ;
    } // for
    
    
    // �p�G�������ѼƳ��OInteger
    if ( allInteger ) {
      // �Ĥ@�ӰѼƧ@���@�}�l�������ǭ�, �M��P���᪺�ѼƤ��
      i_cmp = Integer.parseInt( vec.get( 0 ).GetToken().PrintToken() ) ;
      
      // **** i�q1�}�l
      for ( int i = 1 ; i < vec.size() && allTrue ; i++ ) {
        int i_par = Integer.parseInt( vec.get( i ).GetToken().PrintToken() ) ;
        if ( i_cmp >= i_par ) i_cmp = i_par ;  // ����True, �ç��ǭȳ]���᭱�@�ӰѼ�
        else allTrue = false ;
      } // for
    } // if
    
    
    // �䤤�ܤ֦��@�ӰѼƬOFloat
    else {
      // �Ĥ@�ӰѼƧ@���@�}�l�������ǭ�, �M��P���᪺�ѼƤ��
      f_cmp = Double.parseDouble( vec.get( 0 ).GetToken().PrintToken() ) ;
      
      // **** i�q1�}�l
      for ( int i = 1 ; i < vec.size() && allTrue ; i++ ) {
        double f_par = Double.parseDouble( vec.get( i ).GetToken().PrintToken() ) ;
        if ( f_cmp >= f_par ) f_cmp = f_par ;  // ����True, �ç��ǭȳ]���᭱�@�ӰѼ�
        else allTrue = false ;
      } // for
    } // else
    
    Token t ;
    if ( allTrue ) t = new Token( "#t", 0, 0 ) ;
    else t = new Token( "nil", 0, 0 ) ;
    t.Classify() ;
    ans.SetToken( t ) ;
    
    return ans ;
  } // IsGreaterThanOrEqual()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : <
  // �P�_�Ҧ��ѼƬO�_���p�󥦫᭱���Ѽ�, �O����return #t (true), �_�hreturn nil (false)
  static Node IsLessThan( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    int i_cmp = 0 ;       // �@���������ǭ�
    double f_cmp = 0 ;     // �@���������ǭ�
    boolean allInteger = true, allTrue = true ;
    
    // �P�_�Ѽƫ��O
    // �Y���Ointeger�]���Ofloat, �N�n��XError Message
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      if ( Evaluate.ParameterType( vec.get( i ) ).equals( "integer" ) ) ;
      else if ( Evaluate.ParameterType( vec.get( i ) ).equals( "float" ) )
        allInteger = false ;
      // �D�Ʀr���O���Ѽ�
      else throw new MyException( "ERROR (< with incorrect argument type) : "
                                  + BT.PrintLTree( vec.get( i ), 0, false, "" ), 7 ) ;
    } // for
    
    
    // �p�G�������ѼƳ��OInteger
    if ( allInteger ) {
      // �Ĥ@�ӰѼƧ@���@�}�l�������ǭ�, �M��P���᪺�ѼƤ��
      i_cmp = Integer.parseInt( vec.get( 0 ).GetToken().PrintToken() ) ;
      
      // **** i�q1�}�l
      for ( int i = 1 ; i < vec.size() && allTrue ; i++ ) {
        int i_par = Integer.parseInt( vec.get( i ).GetToken().PrintToken() ) ;
        if ( i_cmp < i_par ) i_cmp = i_par ;  // ����True, �ç��ǭȳ]���᭱�@�ӰѼ�
        else allTrue = false ;
      } // for
    } // if
    
    
    // �䤤�ܤ֦��@�ӰѼƬOFloat
    else {
      // �Ĥ@�ӰѼƧ@���@�}�l�������ǭ�, �M��P���᪺�ѼƤ��
      f_cmp = Double.parseDouble( vec.get( 0 ).GetToken().PrintToken() ) ;
      
      // **** i�q1�}�l
      for ( int i = 1 ; i < vec.size() && allTrue ; i++ ) {
        double f_par = Double.parseDouble( vec.get( i ).GetToken().PrintToken() ) ;
        if ( f_cmp < f_par ) f_cmp = f_par ;  // ����True, �ç��ǭȳ]���᭱�@�ӰѼ�
        else allTrue = false ;
      } // for
    } // else
    
    Token t ;
    if ( allTrue ) t = new Token( "#t", 0, 0 ) ;
    else t = new Token( "nil", 0, 0 ) ;
    t.Classify() ;
    ans.SetToken( t ) ;
    
    return ans ;
  } // IsLessThan()
  // -------------------------------------------------------------------------------

  
  
  // -------------------------------------------------------------------------------
  // Function Name : <=
  // �P�_�Ҧ��ѼƬO�_���p��ε��󥦫᭱���Ѽ�, �O����return #t (true), �_�hreturn nil (false)
  static Node IsLessThanOrEqual( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    int i_cmp = 0 ;       // �@���������ǭ�
    double f_cmp = 0 ;     // �@���������ǭ�
    boolean allInteger = true, allTrue = true ;
    
    // �P�_�Ѽƫ��O
    // �Y���Ointeger�]���Ofloat, �N�n��XError Message
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      if ( Evaluate.ParameterType( vec.get( i ) ).equals( "integer" ) ) ;
      else if ( Evaluate.ParameterType( vec.get( i ) ).equals( "float" ) )
        allInteger = false ;
      // �D�Ʀr���O���Ѽ�
      else throw new MyException( "ERROR (<= with incorrect argument type) : "
                                  + BT.PrintLTree( vec.get( i ), 0, false, "" ), 7 ) ;
    } // for
    
    
    // �p�G�������ѼƳ��OInteger
    if ( allInteger ) {
      // �Ĥ@�ӰѼƧ@���@�}�l�������ǭ�, �M��P���᪺�ѼƤ��
      i_cmp = Integer.parseInt( vec.get( 0 ).GetToken().PrintToken() ) ;
      
      // **** i�q1�}�l
      for ( int i = 1 ; i < vec.size() && allTrue ; i++ ) {
        int i_par = Integer.parseInt( vec.get( i ).GetToken().PrintToken() ) ;
        if ( i_cmp <= i_par ) i_cmp = i_par ;  // ����True, �ç��ǭȳ]���᭱�@�ӰѼ�
        else allTrue = false ;
      } // for
    } // if
    
    
    // �䤤�ܤ֦��@�ӰѼƬOFloat
    else {
      // �Ĥ@�ӰѼƧ@���@�}�l�������ǭ�, �M��P���᪺�ѼƤ��
      f_cmp = Double.parseDouble( vec.get( 0 ).GetToken().PrintToken() ) ;
      
      // **** i�q1�}�l
      for ( int i = 1 ; i < vec.size() && allTrue ; i++ ) {
        double f_par = Double.parseDouble( vec.get( i ).GetToken().PrintToken() ) ;
        if ( f_cmp <= f_par ) f_cmp = f_par ;  // ����True, �ç��ǭȳ]���᭱�@�ӰѼ�
        else allTrue = false ;
      } // for
    } // else
    
    Token t ;
    if ( allTrue ) t = new Token( "#t", 0, 0 ) ;
    else t = new Token( "nil", 0, 0 ) ;
    t.Classify() ;
    ans.SetToken( t ) ;
    
    return ans ;
  } // IsLessThanOrEqual()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : =
  // �P�_�Ҧ��ѼƬO�_�����󥦫᭱���Ѽ�, �O����return #t (true), �_�hreturn nil (false)
  static Node IsEqualTo( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    int i_cmp = 0 ;       // �@���������ǭ�
    double f_cmp = 0 ;     // �@���������ǭ�
    boolean allInteger = true, allTrue = true ;
    
    // �P�_�Ѽƫ��O
    // �Y���Ointeger�]���Ofloat, �N�n��XError Message
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      if ( Evaluate.ParameterType( vec.get( i ) ).equals( "integer" ) ) ;
      else if ( Evaluate.ParameterType( vec.get( i ) ).equals( "float" ) )
        allInteger = false ;
      // �D�Ʀr���O���Ѽ�
      else throw new MyException( "ERROR (= with incorrect argument type) : "
                                  + BT.PrintLTree( vec.get( i ), 0, false, "" ), 7 ) ;
    } // for
    
    
    // �p�G�������ѼƳ��OInteger
    if ( allInteger ) {
      // �Ĥ@�ӰѼƧ@���@�}�l�������ǭ�, �M��P���᪺�ѼƤ��
      i_cmp = Integer.parseInt( vec.get( 0 ).GetToken().PrintToken() ) ;
      
      // **** i�q1�}�l
      for ( int i = 1 ; i < vec.size() && allTrue ; i++ ) {
        int i_par = Integer.parseInt( vec.get( i ).GetToken().PrintToken() ) ;
        if ( i_cmp == i_par ) i_cmp = i_par ;  // ����True, �ç��ǭȳ]���᭱�@�ӰѼ�
        else allTrue = false ;
      } // for
    } // if
    
    
    // �䤤�ܤ֦��@�ӰѼƬOFloat
    else {
      // �Ĥ@�ӰѼƧ@���@�}�l�������ǭ�, �M��P���᪺�ѼƤ��
      f_cmp = Double.parseDouble( vec.get( 0 ).GetToken().PrintToken() ) ;
      
      // **** i�q1�}�l
      for ( int i = 1 ; i < vec.size() && allTrue ; i++ ) {
        double f_par = Double.parseDouble( vec.get( i ).GetToken().PrintToken() ) ;
        if ( f_cmp == f_par ) f_cmp = f_par ;  // ����True, �ç��ǭȳ]���᭱�@�ӰѼ�
        else allTrue = false ;
      } // for
    } // else
    
    Token t ;
    if ( allTrue ) t = new Token( "#t", 0, 0 ) ;
    else t = new Token( "nil", 0, 0 ) ;
    t.Classify() ;
    ans.SetToken( t ) ;
    
    return ans ;
  } // IsEqualTo()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : atom?
  // �P�_���ѼƬO�_���@��atom node, �O����return #t (true), �_�hreturn nil (false)
  static Node IsAtom( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    Token t ;
    Node para = vec.get( 0 ) ;    // �Ѽ� parameter
    if ( para != null && para.GetToken() != null && BT.IsATOM( para.GetToken() ) )
      t = new Token( "#t", 0, 0 ) ;
    
    else t = new Token( "nil", 0, 0 ) ;
    
    t.Classify() ;
    ans.SetToken( t ) ;
    return ans ;
  } // IsAtom()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : pair?
  // �P�_���ѼƬO�_���@��list(S-exp), �O����return #t (true), �_�hreturn nil (false)
  static Node IsPair( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    Token t ;
    Node para = vec.get( 0 ) ;
    if ( para != null && Evaluate.ParameterType( para ).equals( "list" ) )
      t = new Token( "#t", 0, 0 ) ;
    
    else t = new Token( "nil", 0, 0 ) ;
    
    t.Classify() ;
    ans.SetToken( t ) ;
    return ans ;
  } // IsPair()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : list?
  // �P�_���ѼƬO�_���@��pure list(�̥k�䬰nil), �O����return #t (true), �_�hreturn nil (false)
  static Node IsList( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    Token t ;
    Node para = vec.get( 0 ) ;
    if ( para != null && Evaluate.ParameterType( para ).equals( "list" )
         && Evaluate.IsPureList( para ) )
      t = new Token( "#t", 0, 0 ) ;
    
    else t = new Token( "nil", 0, 0 ) ;
    
    t.Classify() ;
    ans.SetToken( t ) ;
    return ans ;
  } // IsList()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : null?
  // �P�_���ѼƬO�_��nil, (), #f
  // �O����return #t (true), �_�hreturn nil (false)
  static Node IsNull( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    Token t ;
    Node para = vec.get( 0 ) ;
    if ( para != null && Evaluate.ParameterType( para ).equals( "nil" ) )
      t = new Token( "#t", 0, 0 ) ;
    
    else t = new Token( "nil", 0, 0 ) ;
    
    t.Classify() ;
    ans.SetToken( t ) ;
    return ans ;
  } // IsNull()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : integer?
  // �P�_���Ѽƪ����O�O�_��integer, �O����return #t (true), �_�hreturn nil (false)
  static Node IsInteger( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    Token t ;
    Node para = vec.get( 0 ) ;
    if ( para != null && Evaluate.ParameterType( para ).equals( "integer" ) )
      t = new Token( "#t", 0, 0 ) ;
    
    else t = new Token( "nil", 0, 0 ) ;
    
    t.Classify() ;
    ans.SetToken( t ) ;
    return ans ;
  } // IsInteger()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : real?  OR  number?
  // �P�_���Ѽƪ����O�O�_��real / number, �O����return #t (true), �_�hreturn nil (false)
  static Node IsReal_or_Number( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    Token t ;
    Node para = vec.get( 0 ) ;
    if ( para != null &&
         ( Evaluate.ParameterType( para ).equals( "integer" ) ||
           Evaluate.ParameterType( para ).equals( "float" ) ) )
      t = new Token( "#t", 0, 0 ) ;
    
    else t = new Token( "nil", 0, 0 ) ;
    
    t.Classify() ;
    ans.SetToken( t ) ;
    return ans ;
  } // IsReal_or_Number()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : string?
  // �P�_���Ѽƪ����O�O�_��string, �O����return #t (true), �_�hreturn nil (false)
  static Node IsString( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    Token t ;
    Node para = vec.get( 0 ) ;
    if ( para != null && Evaluate.ParameterType( para ).equals( "string" ) )
      t = new Token( "#t", 0, 0 ) ;
    
    else t = new Token( "nil", 0, 0 ) ;
    
    t.Classify() ;
    ans.SetToken( t ) ;
    return ans ;
  } // IsString()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : boolean?
  // �P�_���Ѽƪ����O�O�_��boolean, �Y�� #t (true) �Ϊ�  #f / nil (false)
  // �O����return #t (true), �_�hreturn nil (false)
  static Node IsBoolean( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    Token t ;
    Node para = vec.get( 0 ) ;
    if ( para != null &&
         ( Evaluate.ParameterType( para ).equals( "#t" ) ||
           Evaluate.ParameterType( para ).equals( "nil" ) ) )
      t = new Token( "#t", 0, 0 ) ;
    
    else t = new Token( "nil", 0, 0 ) ;
    
    t.Classify() ;
    ans.SetToken( t ) ;
    return ans ;
  } // IsBoolean()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : symbol?
  // �P�_���Ѽƪ����O�O�_��symbol, �O����return #t (true), �_�hreturn nil (false)
  static Node IsSymbol( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    Token t ;
    Node para = vec.get( 0 ) ;
    if ( para != null && Evaluate.ParameterType( para ).equals( "symbol" ) )
      t = new Token( "#t", 0, 0 ) ;
    
    else t = new Token( "nil", 0, 0 ) ;
    
    t.Classify() ;
    ans.SetToken( t ) ;
    return ans ;
  } // IsSymbol()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : string-append
  // ��Ҧ��r��ѼƱ��_�Ӧ����@�Ӧr��, �M��return
  static Node Str_Append( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    String str = "\"" ;     // ����J�@���}�Y�����޸�(")

    // �P�_�Ѽƫ��O
    // �Y���Ostring, �N�n��XError Message
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      if ( Evaluate.ParameterType( vec.get( i ) ).equals( "string" ) ) ;
      // �D�r�ꫬ�O���Ѽ�
      else throw new MyException( "ERROR (string-append with incorrect argument type) : "
                                  + BT.PrintLTree( vec.get( i ), 0, false, "" ), 7 ) ;
    } // for
    
    
    // �̧ǥh���C�ӰѼ��Y�������޸�("), �M�ᱵ�_��
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      String temp =  vec.get( i ).GetToken().PrintToken() ;
      str = str + temp.substring( 1, temp.length() - 1 ) ;
    } // for
    
    str = str + "\"" ;      // ���W�@�����������޸�(")
    
    Token t = new Token( str, 0, 0 ) ;
    t.Classify() ;
    ans.SetToken( t ) ;
    return ans ;
  } // Str_Append()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : string>?
  // �P�_�Ҧ��r��O�_���j�󥦫᭱���r��, �O����return #t (true), �_�hreturn nil (false)
  static Node Str_Greater( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    String s_cmp = "" ;       // �@���������ǭ�
    boolean allTrue = true ;
    
    // �P�_�Ѽƫ��O
    // �Y���Ostring, �N�n��XError Message
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      if ( Evaluate.ParameterType( vec.get( i ) ).equals( "string" ) ) ;
      // �D�r�ꫬ�O���Ѽ�
      else throw new MyException( "ERROR (string>? with incorrect argument type) : "
                                  + BT.PrintLTree( vec.get( i ), 0, false, "" ), 7 ) ;
    } // for
    
    
    // �Ĥ@�ӰѼƧ@���@�}�l�������ǭ�, �M��P���᪺�ѼƤ��
    s_cmp = vec.get( 0 ).GetToken().PrintToken() ;
      
    // **** i�q1�}�l
    for ( int i = 1 ; i < vec.size() && allTrue ; i++ ) {
      String str = vec.get( i ).GetToken().PrintToken() ;
      // compare���G�j��0, ���s_cmp > str
      if ( s_cmp.compareTo( str ) > 0 ) s_cmp = str ;  // ����True, �ç��ǭȳ]���᭱�@�ӰѼ�
      else allTrue = false ;
    } // for
    
    
    Token t ;
    if ( allTrue ) t = new Token( "#t", 0, 0 ) ;
    else t = new Token( "nil", 0, 0 ) ;
    t.Classify() ;
    ans.SetToken( t ) ;
    return ans ;
  } // Str_Greater()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : string<?
  // �P�_�Ҧ��r��O�_���p�󥦫᭱���r��, �O����return #t (true), �_�hreturn nil (false)
  static Node Str_Less( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    String s_cmp = "" ;       // �@���������ǭ�
    boolean allTrue = true ;
    
    // �P�_�Ѽƫ��O
    // �Y���Ostring, �N�n��XError Message
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      if ( Evaluate.ParameterType( vec.get( i ) ).equals( "string" ) ) ;
      // �D�r�ꫬ�O���Ѽ�
      else throw new MyException( "ERROR (string<? with incorrect argument type) : "
                                  + BT.PrintLTree( vec.get( i ), 0, false, "" ), 7 ) ;
    } // for
    
    
    // �Ĥ@�ӰѼƧ@���@�}�l�������ǭ�, �M��P���᪺�ѼƤ��
    s_cmp = vec.get( 0 ).GetToken().PrintToken() ;
      
    // **** i�q1�}�l
    for ( int i = 1 ; i < vec.size() && allTrue ; i++ ) {
      String str = vec.get( i ).GetToken().PrintToken() ;
      // compare���G�p��0, ���s_cmp < str
      if ( s_cmp.compareTo( str ) < 0 ) s_cmp = str ;  // ����True, �ç��ǭȳ]���᭱�@�ӰѼ�
      else allTrue = false ;
    } // for
    
    
    Token t ;
    if ( allTrue ) t = new Token( "#t", 0, 0 ) ;
    else t = new Token( "nil", 0, 0 ) ;
    t.Classify() ;
    ans.SetToken( t ) ;
    return ans ;
  } // Str_Less()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : string=?
  // �P�_�Ҧ��r��O�_���۵�, �O����return #t (true), �_�hreturn nil (false)
  static Node Str_Equal( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    String s_cmp = "" ;       // �@���������ǭ�
    boolean allTrue = true ;
    
    // �P�_�Ѽƫ��O
    // �Y���Ostring, �N�n��XError Message
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      if ( Evaluate.ParameterType( vec.get( i ) ).equals( "string" ) ) ;
      // �D�r�ꫬ�O���Ѽ�
      else throw new MyException( "ERROR (string=? with incorrect argument type) : "
                                  + BT.PrintLTree( vec.get( i ), 0, false, "" ), 7 ) ;
    } // for
    
    
    // �Ĥ@�ӰѼƧ@���@�}�l�������ǭ�, �M��P���᪺�ѼƤ��
    s_cmp = vec.get( 0 ).GetToken().PrintToken() ;
      
    // **** i�q1�}�l
    for ( int i = 1 ; i < vec.size() && allTrue ; i++ ) {
      String str = vec.get( i ).GetToken().PrintToken() ;
      if ( s_cmp.equals( str ) ) s_cmp = str ;  // ����True, �ç��ǭȳ]���᭱�@�ӰѼ�
      else allTrue = false ;
    } // for
    
    
    Token t ;
    if ( allTrue ) t = new Token( "#t", 0, 0 ) ;
    else t = new Token( "nil", 0, 0 ) ;
    t.Classify() ;
    ans.SetToken( t ) ;
    return ans ;
  } // Str_Equal()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : eqv?
  // equivalent ������
  // �P�_����ӰѼƬO���O���V�P�@�ʾ�, �O����return #t (true), �_�hreturn nil (false)
  // �Y�ѼƦP��integer�ΦP��float(�Ynumber), �h�ݬO���O����
  // �Y�O�P��T�ΦP��NIL, �]�Oreturn #t (true)
  static Node IsEquivalent( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    Token t ;
    Node para1 = vec.get( 0 ) ;
    Node para2 = vec.get( 1 ) ;
    
    if ( para1 == para2 ) t = new Token( "#t", 0, 0 ) ;
    else if ( ( Evaluate.ParameterType( para1 ) == "integer" &&
                Evaluate.ParameterType( para2 ) == "integer" ) || 
              ( Evaluate.ParameterType( para1 ) == "float" &&
                Evaluate.ParameterType( para2 ) == "float" ) ) {
      if ( para1.GetToken().PrintToken().equals( para2.GetToken().PrintToken() ) )
        t = new Token( "#t", 0, 0 ) ;
      else t = new Token( "nil", 0, 0 ) ;
    } // else if
    
    else if ( ( Evaluate.ParameterType( para1 ) == "#t" &&
                Evaluate.ParameterType( para2 ) == "#t" ) || 
              ( Evaluate.ParameterType( para1 ) == "nil" &&
                Evaluate.ParameterType( para2 ) == "nil" ) ) 
      t = new Token( "#t", 0, 0 ) ;
    
    else t = new Token( "nil", 0, 0 ) ;
    
    t.Classify() ;
    ans.SetToken( t ) ;
    return ans ;
  } // IsEquivalent()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : equal?
  // equal �ۦP��
  // �P�_����ӰѼƩ�binding����O���O���@��, �O����return #t (true), �_�hreturn nil (false)
  static Node IsEqual( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    Token t ;
    String tree1 = BT.PrintLTree( vec.get( 0 ), 0, false, "" ) ;
    String tree2 = BT.PrintLTree( vec.get( 1 ), 0, false, "" ) ;
    
    if ( tree1.equals( tree2 ) ) t = new Token( "#t", 0, 0 ) ;
    else t = new Token( "nil", 0, 0 ) ;
    
    t.Classify() ;
    ans.SetToken( t ) ;
    return ans ;
  } // IsEqual()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : not
  // �O nil����, return #t (true)
  // �_�hreturn #f / nil (false)
  static Node Not( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    Token t ;
    Node para = vec.get( 0 ) ;
    if ( para != null && Evaluate.ParameterType( para ).equals( "nil" ) )
      t = new Token( "#t", 0, 0 ) ;
    
    else t = new Token( "nil", 0, 0 ) ;
    
    t.Classify() ;
    ans.SetToken( t ) ;
    return ans ;
  } // Not()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : and
  // �̧�evaluate�Ѽ�
  // �u�n���@�ӰѼ�evaluate�������G��nil, �Y���X�j��return nil, ���ݺޫ᭱�Ѽ�evaluate�����G
  // �Y�Ҭ�true�Υievaluate, �hreturn�̫�@�ӰѼ�evaluate�������G
  static Node And( Node node ) throws MyException {
    Node ans = new Node(), ans_temp = new Node() ;
    Token t ;
    boolean allTrue = true ;
    
    // -------------------------- ���o�٥�evaluate���Ѽ� --------------------------
    Vector<Node> vec = new Vector<Node>() ;
    while ( node != null ) {
      if ( node.GetToken() != null && node.GetToken().GetIntType() == Type.NIL ) ;
      
      // �k�`�I�O��ATOM NODE
      else if ( node.GetToken() != null ) throw new MyException( "ERROR (non-list) : ", 11 ) ;
      
      else vec.add( node.GetLeft() ) ;
      
      node = node.GetRight() ;
    } // while
    // -------------------------- ���o�٥�evaluate���Ѽ� --------------------------
    
    for ( int i = 0 ; i < vec.size() && allTrue ; i++ ) {
      try {
        ans_temp = Evaluate.EvalSExp( vec.get( i ), 1 ) ;
      } catch ( MyException e ) {
        // ����evaluate�� No return value
        if ( e.GetCase() == 88 ) 
          throw new MyException( "ERROR (unbound condition) : " + 
                                 BT.PrintLTree( vec.get( i ), 0, false, "" ), 22 ) ;
        else throw e ;
      } // catch
      
      // �u�n���@�ӬOfalse, �Nreturn nil (false)
      if ( ans_temp.GetToken() != null && 
           ans_temp.GetToken().GetIntType() == Type.NIL ) {
        t = new Token( "nil", 0, 0 ) ;
        t.Classify() ;
        ans.SetToken( t ) ;
        allTrue = false ;
      } // if
      
      // else ;
      // �DATOM Node �Ϊ� �Dnil��ATOM Node �N������, �~��P�_�U�@�ӰѼ�
    } // for
      
    if ( allTrue ) 
      ans = Evaluate.EvalSExp( vec.get( vec.size() - 1 ), 1 ) ;

    return ans ;
  } // And()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : or
  // �̧�evaluate�Ѽ� 
  // �u�n���@�ӰѼ�evaluate�������G��true�Υi����
  // �Y�ߨ�return ���Ѽ�evaluate�������G, ���ݺޫ᭱�Ѽ�evaluate�����G
  // �Y�Ҭ�nil(false), �hreturn nil
  static Node Or( Node node ) throws MyException {
    Node ans = new Node(), ans_temp = new Node() ;
    Token t ;
    
    // -------------------------- ���o�٥�evaluate���Ѽ� --------------------------
    Vector<Node> vec = new Vector<Node>() ;
    while ( node != null ) {
      if ( node.GetToken() != null && node.GetToken().GetIntType() == Type.NIL ) ;
      
      // �k�`�I�O��ATOM NODE
      else if ( node.GetToken() != null ) throw new MyException( "ERROR (non-list) : ", 11 ) ;
      
      else vec.add( node.GetLeft() ) ;
      
      node = node.GetRight() ;
    } // while
    // -------------------------- ���o�٥�evaluate���Ѽ� --------------------------
    
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      try {
        ans_temp = Evaluate.EvalSExp( vec.get( i ), 1 ) ;
      } catch ( MyException e ) {
        // ����evaluate�� No return value
        if ( e.GetCase() == 88 ) 
          throw new MyException( "ERROR (unbound condition) : " + 
                                 BT.PrintLTree( vec.get( i ), 0, false, "" ), 22 ) ;
        else throw e ;
      } // catch
      
      // �OATOM Node�S�Ofalse, �N������, �~��P�_�U�@�ӰѼ�
      if ( ans_temp.GetToken() != null &&
           ans_temp.GetToken().GetIntType() == Type.NIL ) ;
      
      // �DATOM Node �Ϊ� �Dnil��ATOM Node�Nreturn���Ѽ�
      else {
        ans = Evaluate.EvalSExp( vec.get( i ), 1 ) ;
        return ans ;
      } // else
    } // for
    
    
    t = new Token( "nil", 0, 0 ) ;
    t.Classify() ;
    ans.SetToken( t ) ;
    return ans ;
  } // Or()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : if
  // �p�G�Ѽ�1(����)evaluate���O true����, �Aevaluate�Ѽ�2��return
  // �Ofalse����, �Aevaluate�Ѽ�3��return
  static Node If( Node node ) throws MyException {
    Node ans = new Node() ;
    
    // -------------------------- ���o�٥�evaluate���Ѽ� --------------------------
    Vector<Node> vec = new Vector<Node>() ;
    while ( node != null ) {
      if ( node.GetToken() != null && node.GetToken().GetIntType() == Type.NIL ) ;
      
      // �k�`�I�O��ATOM NODE
      else if ( node.GetToken() != null ) throw new MyException( "ERROR (non-list) : ", 11 ) ;
      
      else vec.add( node.GetLeft() ) ;
      
      node = node.GetRight() ;
    } // while
    // -------------------------- ���o�٥�evaluate���Ѽ� --------------------------
    
    Node cond = new Node() ;
    // boolean isTrue = false ;
    try {
      cond = Evaluate.EvalSExp( vec.get( 0 ), 1 ) ;  // evaluate�ݬOtrue or false
    } catch ( MyException e ) { 
      if ( e.GetCase() == 88 ) 
        throw new MyException( "ERROR (unbound test-condition) : " + 
                               BT.PrintLTree( vec.get( 0 ), 0, false, "" ), 21 ) ;
      else throw e ;
    } // catch
    
    if ( cond.GetToken() != null && cond.GetToken().GetIntType() == Type.NIL ) {
      if ( vec.size() == 2 )
        throw new MyException( "", 88 ) ;

      else ans = Evaluate.EvalSExp( vec.get( 2 ), 1 ) ;
    } // if
    
    else    // �OTrue �άO �i����
      ans = Evaluate.EvalSExp( vec.get( 1 ), 1 ) ;
    
    return ans ;
  } // If()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : cond (condition)
  // �@��
  // if (...) ....
  // else if (...) ....
  // else if (...) ....
  // else ....
  // ������
  //
  // ���ݰѼƬO���O��'�D'ATOM Node
  // �M��ݨC�ӰѼƸ̭����Fconditon�٦��S����LS-exp, �S���n��error msg
  // �M��̧ǧP�_�C�ӰѼƪ�condition s-exp�O���OTrue�άO�i����
  // 1) �p�G����evaluate���O true�άO�i���檺��
  //    �N����(evaluate)�᭱��sub-exp, �æ^�ǳ̫�@��exp evaluate�������G
  // 2) �p�G����evaluate���Ofalse����, �~��evaluate�U�ӰѼ�, ����(1)&(2)�B�J
  static Node Cond( Node node ) throws MyException {
    
    Vector<Node> vec = new Vector<Node>() ;  // �Ĥ@�h�Ҧ��Ѽ�
    Node ans = null ;
    
    // ------------------------------ ���o�Ĥ@�h�Ҧ��Ѽ� ------------------------------
    while ( node != null ) {  
      // ��̧ܳ���(����)
      if ( node.GetToken() != null && node.GetToken().GetIntType() == Type.NIL ) ;
      
      // �k�`�I�O��ATOM NODE
      else if ( node.GetToken() != null ) throw new MyException( "ERROR (non-list) : ", 11 ) ;
      
      else {
        // Type����list(�DATOM Node)
        if ( node.GetLeft().GetToken() != null ) throw new MyException( "", 87 ) ;
        else
          vec.add( node.GetLeft() ) ;
      } // else
      
      node = node.GetRight() ;
    } // while
    // ------------------------------ ���o�Ĥ@�h�Ҧ��Ѽ� ------------------------------
    
    
    // -------------------------- �P�_�Ĥ@�h�ѼƬO�_�Ҭ���list -------------------------
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      if ( ! Evaluate.IsPureList( vec.get( i ) ) )
        throw new MyException( "Cond format error_para", 222222 ) ;
    } // for
    // -------------------------- �P�_�Ĥ@�h�ѼƬO�_�Ҭ���list -------------------------
    

    // ------------------------------ ���o�ĤG�h�ѼƭӼ� ------------------------------

    for ( int i = 0 ; i < vec.size() ; i ++ ) {
      int node_count = 0 ;
      Node temp = vec.get( i ).GetRight() ;
      boolean isEnd = false ;
      for ( Node head = temp ; head != null && ! isEnd ; head = head.GetRight() ) {
        if ( head.GetToken() != null && head.GetToken().GetIntType() == Type.NIL )
          isEnd = true ;
        // �k�`�I�O��ATOM NODE
        else if ( head.GetToken() != null ) throw new MyException( "lalalalala", 9999 ) ;
        else node_count++ ;
      } // for
    
      // �S���i�^�Ǫ���, ��error msg
      if ( node_count == 0 ) throw new MyException( "", 87 ) ;
    } // for
    // ------------------------------ ���o�ĤG�h�ѼƭӼ� ------------------------------
    
    
    // ------------------------------ �P�_�Ӷi���ӱ��� ------------------------------
    Node cond = null ;
    
    for ( int i = 0 ; i < vec.size() ; i++ ) {
        
      // else .... �����p
      if ( i == vec.size() -1 && vec.get( i ).GetLeft().GetToken() != null
           && vec.get( i ).GetLeft().GetToken().PrintToken().equals( "else" ) ) {

        // -------------------------- ���oevaluate�᪺�ĤG�h�Ѽ� --------------------------
        Vector<Node> sub = new Vector<Node>() ;
        Node temp = vec.get( i ).GetRight() ;
        boolean isEnd = false ;
        for ( Node head = temp ; head != null && ! isEnd ; head = head.GetRight() ) {
          if ( head.GetToken() != null && head.GetToken().GetIntType() == Type.NIL )
            isEnd = true ;
          
          // �k�`�I�O��ATOM NODE
          else if ( head.GetToken() != null ) throw new MyException( "lalalalala", 9999 ) ;
          
          // �榡���T : �n���o�Ӹ`�I
          else sub.add( head.GetLeft() ) ;
        } // for
        // -------------------------- ���oevaluate�᪺�ĤG�h�Ѽ� --------------------------
      
        
        // �S���i�^�Ǫ���, ��error msg
        if ( sub.size() == 0 ) throw new MyException( "", 88 ) ;
        else {
          for ( int k = 0 ; k < sub.size() ; k++ ) {
            try {
              ans = Evaluate.EvalSExp( sub.get( k ), 1 ) ;
            } catch ( MyException e ) {
              if ( e.GetCase() == 88 && k != sub.size() - 1 ) ;
              else throw e ;
            } // catch
          } // for
              
          return ans ;  // for�j��]��, ans�Y�O�̫�@��s-exp���槹�����G
        } // else
      } // if
        
      else cond = Evaluate.EvalSExp( vec.get( i ).GetLeft(), 1 ) ;

        
      // ����evaluate���G��nil�N�����ʧ@�~��P�_�U�ӰѼ�(����)
      if ( cond.GetToken() != null && cond.GetToken().GetIntType() == Type.NIL ) ;
        
      // ������evaluate���G��True�άO�i����
      else {
        // -------------------------- ���oevaluate�᪺�ĤG�h�Ѽ� --------------------------
        Vector<Node> sub = new Vector<Node>() ;
        Node temp = vec.get( i ).GetRight() ;
        boolean isEnd = false ;
        for ( Node head = temp ; head != null && ! isEnd ; head = head.GetRight() ) {
          if ( head.GetToken() != null && head.GetToken().GetIntType() == Type.NIL )
            isEnd = true ;
          
          // �k�`�I�O��ATOM NODE
          else if ( head.GetToken() != null ) throw new MyException( "lalalalala", 9999 ) ;
          
          // �榡���T : �n���o�Ӹ`�I
          else sub.add( head.GetLeft() ) ;
        } // for
        // -------------------------- ���oevaluate�᪺�ĤG�h�Ѽ� --------------------------
      
        
        // �S���i�^�Ǫ���, ��error msg
        if ( sub.size() == 0 ) throw new MyException( "", 88 ) ;
        else {
          for ( int k = 0 ; k < sub.size() ; k++ ) {
            try {
              ans = Evaluate.EvalSExp( sub.get( k ), 1 ) ;
            } catch ( MyException e ) {
              if ( e.GetCase() == 88 && k != sub.size() - 1 ) ;
              else throw e ;
            } // catch
          } // for
              
          return ans ;  // for�j��]��, ans�Y�O�̫�@��s-exp���槹�����G
        } // else
      } // else
      
    } // for
    
    throw new MyException( "", 88 ) ;
    // ------------------------------ �P�_�Ӷi���ӱ��� ------------------------------
    
  } // Cond()
  // -------------------------------------------------------------------------------
  
  
  // -------------------------------------------------------------------------------
  // Function Name : begin
  // �C�ӰѼƨ̧�evaluate
  // return�̫�@�ӰѼ�evaluate�������G(����Ҧ��Ʊ��������F)
  static Node Begin( Node node ) throws MyException {
    Vector<Node> vec = new Vector<Node>() ;
    Node ans = new Node() ;
    
    // --------------------- ����evaluate�� S-exp ---------------------
    while ( node != null ) {
      if ( node.GetToken() != null && node.GetToken().GetIntType() == Type.NIL ) ;
      
      // �k�`�I�O��ATOM NODE
      else if ( node.GetToken() != null ) throw new MyException( "ERROR (non-list) : ", 11 ) ;
      
      else vec.add( node.GetLeft() ) ;
      
      node = node.GetRight() ;
    } // while
    // --------------------- ����evaluate�� S-exp ---------------------
    
    
    // -------------------- evaluate & ���� S-exp --------------------
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      try {
        ans = Evaluate.EvalSExp( vec.get( i ), 1 ) ;
      } catch ( MyException e ) {
        // evaluate�� no return value
        if ( e.GetCase() == 88 && i != vec.size() - 1 ) ;
        else throw e ;
      } // catch
    } // for
    // -------------------- evaluate & ����  S-exp --------------------
    
    return ans ;
  } // Begin()
  // -------------------------------------------------------------------------------
  
  
  // -------------------------------------------------------------------------------
  // Function Name : verbose?
  // ��verbose mode�O�_�}��
  static Node IsVerbose() throws MyException {
    Token t ;
    if ( Main.sIsVerbose ) t = new Token( "#t", 0, 0 ) ;
    else t = new Token( "nil", 0, 0 ) ;

    t.Classify() ;
    return new Node( t ) ;
  } // IsVerbose()
  // -------------------------------------------------------------------------------
  
  
  // -------------------------------------------------------------------------------
  // Function Name : verbose
  // �ݶǶi�Ӫ����ӰѼƬO�_��nil
  // �p�G�Onil�h����verbose mode, �Y�Dnil�h�}��verbose mode
  static Node Verbose( Vector<Node> vec ) throws MyException {
    if ( Evaluate.ParameterType( vec.get( 0 ) ).equals( "nil" ) )
      Main.sIsVerbose = false ;
    else Main.sIsVerbose = true ;
    
    return IsVerbose() ;
  } // Verbose()
  // -------------------------------------------------------------------------------
  
  
  // -------------------------------------------------------------------------------
  // Function Name : lambda
  // �w�q�@����function
  // format: ( lambda ( zero-or-more-symbols ) one-or-more-S-expressions )
  // format��error�Y��"LAMBDA format error"��error msg
  
  // �P�_Lambda format : ( lambda ( zero-or-more-symbols ) one-or-more-S-expressions )
  // 1.�P�_�Ĥ@�ӰѼƬO�_��list or () [nil]
  // 2.�Ĥ@�ӰѼƸ̭��O�_���Osymbol
  static Node Lambda( Node node ) throws MyException {
    Token t ;
    Node para1 = node.GetLeft() ;
    Vector<DefObject> f_para = new Vector<DefObject>() ;
    int para_num = 0 ;
    
    // () �L�Ѽƪ����p********************************************
    if ( para1 != null && para1.GetToken() != null &&
         para1.GetToken().GetIntType() == Type.NIL )
      t = new Token( "#<procedure lambda>", 0, 0 ) ;
    // () �L�Ѽƪ����p********************************************
    
    // ���Ѽƪ����p***********************************************
    // �P�_�O���O�̭����F�賣�Osymbol
    else if ( Evaluate.ParameterType( para1 ).equals( "list" ) ) {
      while ( para1 != null ) {
        if ( para1.GetToken() != null && para1.GetToken().GetIntType() == Type.NIL ) ;
        
        // �k�`�I�O��ATOM NODE
        else if ( para1.GetToken() != null ) throw new MyException( "", 87 ) ;
        
        else if ( para1.GetLeft() != null && para1.GetLeft().GetToken() != null 
                  && para1.GetLeft().GetToken().GetIntType() != Type.SYMBOL )
          throw new MyException( "", 87 ) ;
        
        // ��8�D����case  ex: ( lambda ( ( cons 1 2 ) x u ) ( + x u ) )
        else if ( para1.GetLeft().GetToken() == null ) throw new MyException( "", 87 ) ;
        
        else {
          para_num ++ ;
          Node nu = null ;
          DefObject p = new DefObject( para1.GetLeft().GetToken().PrintToken(), 
                                       "symbol", nu ) ;
          f_para.add( p ) ;
        } // else
        
        
        para1 = para1.GetRight() ;
      } // while
      
      t = new Token( "#<procedure lambda>", 0, 0 ) ;
    } // else if
    // ���Ѽƪ����p***********************************************
    
    else throw new MyException( "", 87 ) ;
    
    
    // ==================== function body ====================
    
    Vector<Node> sxp = new Vector<Node>() ;
    Node para = node.GetRight() ;
    
    while ( para != null ) {
      if ( para.GetToken() != null && para.GetToken().GetIntType() == Type.NIL ) ;
      
      // �k�`�I�O��ATOM NODE
      else if ( para.GetToken() != null ) throw new MyException( "", 87 ) ;
      
      else sxp.add( para.GetLeft() ) ;
      
      para = para.GetRight() ;
    } // while
    
    // ==================== function body ====================
    
    Main.sLambda_Temp = new DefObject( "lambda", "function_lambda",
                                       new Node( new Token( "#<procedure lambda>", 0, 0 ) ),
                                       para_num, "s-exp", f_para, sxp  ) ;
    
    t.Classify();
    return new Node( t ) ;
  } // Lambda()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : User_Function

  static Node User_Function( Node node, DefObject def ) throws MyException {

    boolean isHavePara = true ;
    Vector<DefObject> vec_para = null ;
    Node e_left = null ;
    
    // �����ǤJ�Ѽƪ�function
    if ( def.mFunction_Parameter == null || 
         ( def.mFunction_Parameter != null && def.mFunction_Parameter.isEmpty() ) )
      isHavePara = false ;
    
    // ____________________________ ���o�Ѽ�(�w�q�ϰ��ܼ�) ____________________________
    
    if ( isHavePara ) vec_para = new Vector<DefObject>() ;
    
    int i = 0 ;
    while ( node != null ) {  
      // ��̧ܳ���(����)
      if ( node.GetToken() != null && node.GetToken().GetIntType() == Type.NIL ) ;
      
      // �k�`�I�O��ATOM NODE
      else if ( node.GetToken() != null ) throw new MyException( "ERROR (non-list) : ", 11 ) ;
      
      else if ( node.GetLeft() != null ) {
        if ( isHavePara ) {
          
          try {
            
            e_left = Evaluate.EvalSExp( node.GetLeft(), 1 ) ;
            
          } catch ( MyException e ) {
            // �Ѽ�evaluate�� No return value -> error
            if ( e.GetCase() == 88 ) {
              throw new MyException( "ERROR (unbound parameter) : " + 
                                     BT.PrintLTree( node.GetLeft(), 0, false, "" ), 20 ) ;
            } // if
            else throw e ;
          } // catch
          
          DefObject d = Evaluate.FindDefine( e_left, true ) ;
          String p_name = def.GetFunctionParameter().get( i ).GetName() ;
          
          // �p�G�ѼƬO function
          if ( d != null && ( d.mType.equals( "function" ) || d.mType.equals( "function_user" )
                              || d.mType.equals( "function_lambda" ) ) ) {
            
            DefObject p_def  = new DefObject( p_name, d.GetType(), d.GetBinding(), 
                                              d.GetParameterNumber(), d.GetParameterType(), 
                                              d.GetFunctionParameter(), d.GetFunctionBody() ) ;
            vec_para.add( p_def ) ;
          } // if
          
          
          else { // symbol
            DefObject p_def  = new DefObject( p_name, "symbol",  e_left ) ;
            vec_para.add( p_def ) ;
          } // else
          
        } // if
        
        else throw new MyException( "No Para But Input Para", 444444444 ) ;
        
      } // else if
      
      i ++ ;
      node = node.GetRight() ;
    } // while
    
    
    
    if ( isHavePara ) {
      if ( Main.sLocal_Def == null ) Main.sLocal_Def = new Vector<Vec_DefObject>() ;
      Vec_DefObject v = new Vec_DefObject() ;
      v.SetVec( vec_para ) ;
      Main.sLocal_Def.add( v ) ;
    } // if
    // ____________________________ ���o�Ѽ�(�w�q�ϰ��ܼ�) ____________________________
    
    
    // ____________________________ ����function body ____________________________
    Node ans = null ;
    for ( int j = 0 ; j < def.mFunction_Body.size() ; j ++ ) {

      try {
        ans = Evaluate.EvalSExp( def.mFunction_Body.get( j ), 1 ) ;
      } catch ( MyException e ) {
        if ( e.GetCase() == 88 && j != def.mFunction_Body.size() - 1 ) ;
        else throw e ;
      } // catch 
      
    } // for
    // ____________________________ ����function body ____________________________
   

    if ( isHavePara )
      Main.sLocal_Def.remove( Main.sLocal_Def.size() - 1 ) ;   // �B�z���opart�Npop���o���w�q���Ѽ�
    
    return ans ;
  } // User_Function()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : let
  // �w�q�@����function��ϰ��ܼ� (�Ȧb�᭱��function body����)
  // format: ( let '(' { SYMBOL <S-exp> } ')'  one-or-more-S-expressions )
  // format��error�Y��"LAMBDA format error"��error msg
  
  // �P�_Let format : ( let '(' { SYMBOL <S-exp> } ')'  one-or-more-S-expressions )
  // 1.�P�_�Ĥ@�ӰѼƬO�_��list or () [nil]
  // 2.�Ĥ@�ӰѼƸ̭��O�_���O'( SYMBOL <S-exp> )'���榡
  
  static Node Let( Node node ) throws MyException {
    Node para1 = node.GetLeft() ;    // �ϰ��ܼƪ��w�q��
    Vector<DefObject> f_para = new Vector<DefObject>() ;   // �x�s�w�q�����ϰ��ܼ�
    int para_num = 0 ;
    boolean havePara = true ;
    
    // () �L�Ѽƪ����p********************************************
    if ( para1 != null && para1.GetToken() != null &&
         para1.GetToken().GetIntType() == Type.NIL ) havePara = false ;
    // () �L�Ѽƪ����p********************************************
    
    // ���Ѽƪ����p***********************************************
    // �P�_�O���O�̭����F�賣�Osymbol
    else if ( Evaluate.ParameterType( para1 ).equals( "list" ) ) {
      while ( para1 != null ) {
        if ( para1.GetToken() != null && para1.GetToken().GetIntType() == Type.NIL ) ;
        
        // �k�`�I�O��ATOM NODE
        else if ( para1.GetToken() != null ) throw new MyException( "", 87 ) ;
        
        else {
          Node p = para1.GetLeft() ;   // ���ϰ��ܼƪ��w�q   ex: (x 5) , (y 4)....
          // System.out.println(BT.PrintLTree(p, 0, false, "")) ;
          
          // ���ŦX ( SYMBOL <S-exp> ) �o�˪��榡      ex: ( x 5 )
          if ( p != null && p.GetToken() == null && p.GetLeft() != null && 
               p.GetRight() != null && p.GetRight().GetToken() == null ) {
            
            // ���ŦXlist �άO ���W�X��Ӹ`�I(ex: (x 11 12 )) 
            // �άO ���ܼƦ��S���w�qBinding ( p.GetRight().GetLeft() == null )
            if ( ( p.GetRight().GetRight() != null && p.GetRight().GetRight().GetToken() != null
                   && p.GetRight().GetRight().GetToken().GetIntType() != Type.NIL ) ||
                 ( p.GetRight().GetRight() != null && p.GetRight().GetRight().GetToken() == null
                   && p.GetRight().GetRight().GetLeft() != null )
                 || p.GetRight().GetLeft() == null )
              throw new MyException( "", 87 ) ;
            
            Node var_name = p.GetLeft() ;
            Node var_binding = p.GetRight().GetLeft() ;
            
            // �n�Q�w�q���ϰ��ܼƶ���SYMBOL
            if ( var_name != null && var_name.GetToken() != null && 
                 var_name.GetToken().GetIntType() == Type.SYMBOL ) {
              
              DefObject isDefined = Evaluate.FindDefine( var_name, true ) ;
              // �W�r��Binding�۵�����    ex : (let ( (x (read)) (car x))  (3 5) )
              // �n�w�q��symbol���t�Τ���function���W�r -> error
              if ( isDefined != null && isDefined.GetType().equals( "function" ) ) 
                throw new MyException( "", 87 ) ;
              
              para_num ++ ;
              DefObject d = new DefObject( var_name.GetToken().PrintToken(), 
                                           "symbol", var_binding ) ;
              f_para.add( d ) ;
              
            } // if
            
            // �w�q�DSYMBOL���ܼ� -> error
            else throw new MyException( "", 87 ) ;
          } // if
          
          // ���ŦX ( SYMBOL <S-exp> ) �o�˪��榡 -> error
          else throw new MyException( "", 87 ) ;
        } // else
 
        para1 = para1.GetRight() ;
      } // while()
    } // else if
    // ���Ѽƪ����p***********************************************
    
    else throw new MyException( "", 87 ) ;
    
    
    // ���ˬdformat�Aevaluate�ϰ��ܼƪ�Binding
    for ( int i = 0 ; i < f_para.size() ; i++ ) {
      try {

        f_para.get( i ).SetBinding( Evaluate.EvalSExp( f_para.get( i ).GetBinding(), 1 ) ) ;

      } catch ( MyException e ) {
        // �n�w�q��binding evaluate�� No return value -> error
        if ( e.GetCase() == 88 ) 
          throw new MyException( "ERROR (no return value) : " + 
                                 BT.PrintLTree( f_para.get( i ).GetBinding(), 0, false, "" ), 23 ) ;
        else throw e ;
      } // catch
    } // for

    
    // ��w�q�����ϰ��ܼ�push�istack
    if ( havePara ) {
      if ( Main.sLocal_Def == null ) Main.sLocal_Def = new Vector<Vec_DefObject>() ;
      Vec_DefObject v = new Vec_DefObject() ;

      v.SetVec( f_para ) ;
      Main.sLocal_Def.add( v ) ;
    } // if
    
    // ==================== ���o function body ====================
    
    Vector<Node> f_body = new Vector<Node>() ;
    Node sxp = node.GetRight() ;         // function body
    
    while ( sxp != null ) {
      if ( sxp.GetToken() != null && sxp.GetToken().GetIntType() == Type.NIL ) ;
      
      // �k�`�I�O��ATOM NODE
      else if ( sxp.GetToken() != null ) throw new MyException( "", 87 ) ;
      
      else f_body.add( sxp.GetLeft() ) ;
      
      sxp = sxp.GetRight() ;
    } // while
    
    // ==================== ���o function body ====================
    
    
    // ____________________________ ����function body ____________________________
    Node ans = null ;
    for ( int j = 0 ; j < f_body.size() ; j ++ ) {
      try {
        ans = Evaluate.EvalSExp( f_body.get( j ), 1 ) ;
      } catch ( MyException e ) {
        if ( e.GetCase() == 88 && j != f_body.size() - 1 ) ;
        else throw e ;
      } // catch 
    } // for
    // ____________________________ ����function body ____________________________
    
    
    if ( havePara )
      Main.sLocal_Def.remove( Main.sLocal_Def.size() - 1 ) ;   // �B�z���opart�Npop���o���w�q���ϰ��ܼ�
    
    return ans ;
  } // Let()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : read
  // Ū���᭱��J��input (�@�ʧ��㪺��)
  static Node Read() throws MyException {
    Node ans = new Node() ;
    
    // ========================== read�e���ˬd��r�ƪ�l�� ==========================
    // �ئn�@�ʾ��, Column_re�n�k�s
    // �p�G�P�@�楼�B�z���ؾ�ҥΪ�Line_re�ܬ�1
    // �p�G�B�z���άO�ѤU�OWhite Space�ε��ѫh�]��0
    if ( GT.BehindIsWSOrComment( Main.sColumn ) )
      Main.sLine_re = 0 ;
    else Main.sLine_re = 1 ;
    Main.sColumn_re = 0 ;
    // ========================== read�e���ˬd��r�ƪ�l�� ==========================
    
    try {
      ans = BT.ReadSExp( Main.s_oReader ) ;
    } catch( MyException e ) {
      if ( e.GetCase() == 1 || e.GetCase() == 2 || e.GetCase() == 4 || e.GetCase() == 5 ) {
        String s = "\"" + e.getMessage().substring( 0, e.getMessage().length()-1 ) + "\"" ;
        Token t = new Token( s, 0, 0 ) ;
        t.Classify() ;
        t.SetType( Type.ERROR ) ;
        ans = new Node( t ) ;
        
        // syntax error : Error�o�ͪ��ɭ�,��ʾ�n�ᱼ�M��,�o��input�]���n,line_re��column�]�n�k�s
        // �o���ˬd��M�Ū���] : �J��error�Y��msg, �ɭP�᭱��input�S�M���|���U��Ūs-exp�ɥX��
        Main.sLine_re = 0 ;
        Main.sColumn_re = 0 ;
        if ( e.GetCase() <= 5 ) {
          Main.sStr = null ;
          Main.sColumn = 0 ;
        } // if  ( syntax error )
        
        return ans ;
      } // if
      else {
        System.out.println( "Read Other Error" ) ;
        throw e ;
      } // else
    } // catch
    
    if ( ! Evaluate.IsPureList( ans ) )
      throw new MyException( "ERROR (non-list) : ", 11 ) ;
    
    return ans ;
  } // Read()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : write
  // ��X�᭱��۪��Ѽ� (�@�ʧ��㪺��)
  static Node Write( Vector<Node> vec ) throws MyException {
    Node para = vec.get( 0 ) ;
    
    if ( ! Evaluate.IsPureList( para ) )
      throw new MyException( "ERROR (non-list) : ", 11 ) ;
    else {
      String str = BT.PrintLTree( para, 0, false, "" ) ;
      System.out.print( str.substring( 0, str.length()-1 ) ) ;  // ���t����Ÿ�
    } // else

    return para ;
  } // Write()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : eval
  // Evaluate�᭱��۪��Ѽ�
  // ���Mvec�Ƕi�ӫe���Qevaluate�F, ���n�A�@��evaluate���ӰѼ�
  static Node Eval( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    // level�]��0�O�]���O���s��evaluate, �G��top level
    ans = Evaluate.EvalSExp( vec.get( 0 ), 0 ) ;

    return ans ;
  } // Eval()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : set!
  // �]�w�Ѽƪ��N�q, parameter1���w�q��parameter2
  // �t�@�Ӫ�����define, �����@�w�n�btop level
  static Node Set( Node node ) throws MyException {
    // _______________________________ �ܼƫŧi�� _______________________________
    
    Node para1 = node.GetLeft() ;              // �Ѽ�1, �Q�w�q��symbol
    Node para2 = node.GetRight().GetLeft() ;   // �Ѽ�2, Binding (value)
                                                                                
    DefObject def1 = null, def2 = null, new_def = null ;                        
    Boolean para1_IsDefined = false ;          // �Ѽ�1���S���Q�w�q�L (�YsVec_Def�s���s�b�Ѽ�1)
    Boolean isDefinedinLocal_Let = false ;
    Boolean isDefinedinParameter = false ;
    Boolean isDefinedinGlobal = false ;
    Boolean isFind = false ;                   // �@��즳�Q�w�q�L���N�X�j��, ���M��function���^�ɷ|����e�����w�q
    int stack = -1 ;          // �O���Q�w�q�L�B�s�b��ϰ쪺���hstack

    // _______________________________ �ܼƫŧi�� _______________________________
    
    
    // _______________________________ �B�z�Ѽ�1 _______________________________
    
    // �Ѽ�1�OSymbol
    if ( para1.GetToken() != null && para1.GetToken().GetIntType() == Type.SYMBOL ) {
      // �p�G�n�w�q�@��symbol��binding, define�᭱�u������ӰѼ�(symbol name & binding)
      // �Y����ӥH�W���ѼƧY��Xerror msg
      if ( node.GetRight().GetRight() != null && node.GetRight().GetRight().GetToken() == null
           && node.GetRight().GetRight().GetLeft() != null )
        throw new MyException( "", 87 ) ;

      // �쥻�g�k : def1 = Evaluate.FindDefine( para1, true ) ;
      
      // ==================== �P�_�Ѽ�1�O�_�Q�w�q�L & �Q�w�q�b���� ====================
      
      String name = para1.GetToken().PrintToken() ;
      
      // �p�G�O����Let Function �B ���w�q�ϰ��ܼ�
      if ( Main.sIsLetFunction && Main.sLocal_Def != null && ! Main.sLocal_Def.isEmpty() ) {
        for ( int i = Main.sLocal_Def.size() - 1 ; i > - 1 && ! isFind ; i-- ) {
          for ( int j = 0 ; j < Main.sLocal_Def.get( i ).GetVec().size() ; j++ ) {
            // set : �W�r�۵��~�⦳���
            if ( name.equals( Main.sLocal_Def.get( i ).GetVec().get( j ).GetName() ) ) {
              isDefinedinLocal_Let = true ;
              para1_IsDefined = true ;
              stack = i ;
              def1 = Main.sLocal_Def.get( i ).GetVec().get( j ) ;
              isFind = true ;
            } // if
          } // for
        } // for
      } // if
      
      
      // �p�G���O����Let Function �B ���w�q�Ѽ� 
      // lambda �� define�X�Ӫ��ϥΪ̦۩w�qfunction, �p�G�ܼƦb�ѼƤ��S�����w�q, �N�h�����
      // �o��O�u�b�ѼƧ�  ( stack�̷spush�i�Ӫ��@�h )
      else if ( ! Main.sIsLetFunction && Main.sLocal_Def != null && ! Main.sLocal_Def.isEmpty() ) {
        for ( int j = 0 ; j < Main.sLocal_Def.lastElement().GetVec().size() ; j++ ) {
          // set : �W�r�۵��~�⦳���
          if ( name.equals( Main.sLocal_Def.lastElement().GetVec().get( j ).GetName() ) ) {       
            isDefinedinParameter = true ;
            para1_IsDefined = true ;
            def1 = Main.sLocal_Def.lastElement().GetVec().get( j ) ;
            isFind = true ;
          } // if
        } // for
      } // else if
      
      
      for ( int i = 0 ; i < Main.sVec_Def.size() ; i++ ) {
        // set : �W�r�۵��~�⦳���
        if ( name.equals( Main.sVec_Def.get( i ).GetName() ) ) {    
          isDefinedinGlobal = true ;
          para1_IsDefined = true ;
          def1 = Main.sVec_Def.get( i ) ;
          isFind = true ;
        } // if
      } // for
      // ==================== �P�_�Ѽ�1�O�_�Q�w�q�L & �Q�w�q�b���� ====================
      
    } // if

    // define format error
    else throw new MyException( "", 87 ) ;
    
   
    // ------------------- �ݰѼ�1���S���Q�w�q�L -------------------
    // �Q�w�q�L�B�D����function
    if ( def1 != null && ! def1.GetType().equals( "function" ) ) {
      para1_IsDefined = true ;
      // ���O�ۦ��Q�w�q�L,�٤���remove��,�]���p�G�Ѽ�2���Ψ�쥻���w�q�N�䤣��쥻���w�q�F
      // Main.sVec_Def.remove( def1 ) ;
    } // if

    // �Q�w�q�L�B������function
    else if ( def1 != null && def1.GetType().equals( "function" ) )
      // �Ѽ�1���t�Τ���Function
      throw new MyException( "", 87 ) ;
    
    // def1 == null
    else ;
    // ------------------- �ݰѼ�1���S���Q�w�q�L -------------------
    // _______________________________ �B�z�Ѽ�1 _______________________________
    
    
    // _______________________________ �B�z�Ѽ�2 _______________________________
    
    Node para2_eval = new Node() ;
    try {
      para2_eval = Evaluate.EvalSExp( para2, 1 ) ;
    } catch ( MyException e ) {
      // �n�w�q��binding evaluate�� No return value -> error
      if ( e.GetCase() == 88 ) 
        throw new MyException( "ERROR (no return value) : " + 
                               BT.PrintLTree( para2, 0, false, "" ), 23 ) ;
      else throw e ;
    } // catch
    
    if ( para2_eval.GetToken() != null ) {
      def2 = Evaluate.FindDefine( para2_eval, false ) ;
      // ���F�B�z (define x (quote a)) , �ҥHFindDefine�Ѽƶ�false
      // �nbinding��token�@�ˤ~��Q�w�q�L, �W�r��token�@�� ���Υh�䥦��binding
      // �]�� 'a �N�� 'cons �@��, �N�� cons �ݰ_�ӬO���w�q�L, ���ƹ�W���O binding �� #<procedure cons>
    } // if
      

    
    // �Ѽ�2�O���Fsymbol�~��atom node (int,float,string,#t,nil)
    // ���ݭn��def2���S�����, ���M (define a 5 ) (define b a ) �o��case def2���Dnull
    // ����binding
    if ( para2_eval.GetToken() != null && Evaluate.IsATOM( para2_eval.GetToken() ) ) {
      // System.out.println("fdfd") ;
      new_def = new DefObject( para1.GetToken().PrintToken(), "symbol", para2_eval ) ;
    } // if
    
    
    // Define + Lambda
    else if ( para2_eval.GetToken() != null &&
              para2_eval.GetToken().PrintToken().equals( "#<procedure lambda>" ) ) {
      
      // (define x (lambda ( a b ) ( + a 5 ) ( + b 5 )))
      if ( def2 == null ) {
        new_def = Main.sLambda_Temp ;
        new_def.SetName( para1.GetToken().PrintToken() ) ;
      } // if
      
      // (define x (lambda ( a b ) ( + a 5 ) ( + b 5 )))
      // (define y x )
      else {
        new_def = new DefObject( para1.GetToken().PrintToken(), def2.GetType(), def2.GetBinding(), 
                                 def2.GetParameterNumber(), def2.GetParameterType(), 
                                 def2.GetFunctionParameter(), def2.GetFunctionBody() ) ;
      } // else  
    } // else if
    
    // �Ѽ�2�O�g�Lquote�B�z�L��symbol atom node
    // ex : (define a 'hi)
    else if ( para2_eval.GetToken() != null && def2 == null
              && para2_eval.GetToken().GetIntType() == Type.SYMBOL )
      new_def = new DefObject( para1.GetToken().PrintToken(), "symbol", para2_eval ) ;
    
    // �Ѽ�2�O�DATOM Node �B �S�Q�w�q�L  ( �p : (1 . 2) )
    // ����binding
    else if ( para2_eval.GetToken() == null && def2 == null )
      new_def = new DefObject( para1.GetToken().PrintToken(), "symbol", para2_eval ) ;
    
    // �Ѽ�2�O�Q�w�q�L��function
    // ����binding (defObj��type��function_user)
    else if ( def2 != null && ( def2.GetType().equals( "function" ) || 
                                def2.GetType().equals( "function_user" ) ) ) {
      new_def = new DefObject( para1.GetToken().PrintToken(), "function_user", 
                               def2.GetParameterNumber(), def2.GetParameterType() ) ;
      new_def.SetBinding( def2.GetBinding() ) ;
    } // else if
    
    
    // �Ѽ�2�O�Q�w�q�L��function�B���ϥΪ̦ۤv�w�q��function
    // ����binding (defObj��type��function_lambda)
    else if ( def2 != null && def2.GetType().equals( "function_lambda" ) ) {
      new_def = new DefObject( para1.GetToken().PrintToken(),
                               "function_lambda", def2.GetBinding(), 
                               def2.GetParameterNumber(), def2.GetParameterType(),
                               def2.GetFunctionParameter(), def2.GetFunctionBody() ) ;
    } // else if
    
    
    // ERROR Object
    else if ( para2_eval.GetToken() != null && para2_eval.GetToken().GetIntType() == Type.ERROR ) {
      new_def = new DefObject( para1.GetToken().PrintToken(), "symbol", para2_eval ) ;
    } // if
    
    
    // ************************
    else throw new MyException( "No Binding", 2222 ) ;
    // ************************
    
    // _______________________________ �B�z�Ѽ�2 _______________________________
    
    
    // _______________________________ �B�z�^�ǭ�  _______________________________
    if ( isDefinedinParameter && para1_IsDefined ) {
      Main.sLocal_Def.lastElement().GetVec().remove( def1 ) ;
      Main.sLocal_Def.lastElement().GetVec().add( new_def ) ;
    } // if
    
    else if ( isDefinedinLocal_Let && para1_IsDefined ) {
      Main.sLocal_Def.get( stack ).GetVec().remove( def1 ) ;
      Main.sLocal_Def.get( stack ).GetVec().add( new_def ) ;
    } // else if
    
    else if ( isDefinedinGlobal && para1_IsDefined ) {
      Main.sVec_Def.remove( def1 ) ;
      Main.sVec_Def.add( new_def ) ;
    } // else if

    else Main.sVec_Def.add( new_def ) ;
    
      
    return para2_eval ;
    // _______________________________ �B�z�^�ǭ�  _______________________________

  } // Set()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : create-error-object
  // �Ыؤ@��Error-Object
  // �Ѽƫ��O�u����String, ��l�h��Xerror msg
  static Node C_Error_Object( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    if ( ! Evaluate.ParameterType( vec.get( 0 ) ).equals( "string" ) )
      throw new MyException( "ERROR (create-error-object with incorrect argument type) : "
                             + BT.PrintLTree( vec.get( 0 ), 0, false, "" ), 7 ) ;
    else {
      vec.get( 0 ).GetToken().SetType( Type.ERROR ) ;
      ans = vec.get( 0 ) ;
    } // else
      
    return ans ;
  } // C_Error_Object()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : error-object?
  // �P�_�ѼƬO�_��error-object, �O�h�^�� #t (true), �_�h�^�� nil (false)
  static Node IsErrorObject( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    Token t ;
    Node para = vec.get( 0 ) ;
    if ( para != null && Evaluate.ParameterType( para ).equals( "error" ) )
      t = new Token( "#t", 0, 0 ) ;
    
    else t = new Token( "nil", 0, 0 ) ;
    
    t.Classify() ;
    ans.SetToken( t ) ;
    return ans ;
  } // IsErrorObject()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : display-string
  // �P�_�ѼƬO�_��string �� error-object, �_�h��error msg
  // print ���t�e�����޸���token���e, ��return�Ѽ�node
  static Node Display_String( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    Node para = vec.get( 0 ) ;
    if ( ! Evaluate.ParameterType( para ).equals( "string" ) &&
         ! Evaluate.ParameterType( para ).equals( "error" ) )
      throw new MyException( "ERROR (display-string with incorrect argument type) : "
                             + BT.PrintLTree( para, 0, false, "" ), 7 ) ;

    else {
      String str = para.GetToken().PrintToken() ;
      System.out.print( str.substring( 1, str.length()-1 ) ) ;   // �h���e�����޸�
      ans = para ;
    } // else
    
    return ans ;
  } // Display_String()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : newline
  // print '\n' , ��return nil
  static Node NewLine() throws MyException {
    System.out.println() ;
    
    Token t ;
    t = new Token( "nil", 0, 0 ) ;
    t.Classify() ;
    return new Node( t ) ;
  } // NewLine()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : symbol->string
  // �u����symbol�Ѽ�, �ΥHstring���O���覡return�Ѽ�
  static Node SymbolToString( Vector<Node> vec ) throws MyException {
    Node para = vec.get( 0 ) ;
    if ( para != null && ! Evaluate.ParameterType( para ).equals( "symbol" ) )
      throw new MyException( "ERROR (symbol->string with incorrect argument type) : "
                             + BT.PrintLTree( para, 0, false, "" ), 7 ) ;
    Token t ;
    String str = "\"" + para.GetToken().PrintToken() + "\"" ;    // �[�W�e�����޸��ܦ�string
    t = new Token( str, 0, 0 ) ;
    t.Classify() ;
    return new Node( t ) ;
  } // SymbolToString()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : number->string
  // �u����symbol�Ѽ�, �ΥHstring���O���覡return�Ѽ�
  static Node NumberToString( Vector<Node> vec ) throws MyException {
    Node para = vec.get( 0 ) ;
    if ( ! Evaluate.ParameterType( para ).equals( "integer" ) &&
         ! Evaluate.ParameterType( para ).equals( "float" ) )
      throw new MyException( "ERROR (number->string with incorrect argument type) : "
                             + BT.PrintLTree( para, 0, false, "" ), 7 ) ;
    Token t ;
    String str = "\"" + para.GetToken().PrintToken() + "\"" ;    // �[�W�e�����޸��ܦ�string
    t = new Token( str, 0, 0 ) ;
    t.Classify() ;
    return new Node( t ) ;
  } // NumberToString()
  // -------------------------------------------------------------------------------
  
  
} // class MyFunctions
