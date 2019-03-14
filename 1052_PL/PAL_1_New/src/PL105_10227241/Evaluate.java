package PL105_10227241;

import java.util.Vector;

class Evaluate {
  
  // ��l�ƥ��ӴN�w�q�n��Function
  static Vector<DefObject> InitDefObject() {
    Vector<DefObject> def = new Vector<DefObject>() ;
    
    DefObject cons = new DefObject( "cons", "function", 2, "S-exp" ) ; 
    DefObject list = new DefObject( "list", "function", 9999, "S-exp" ) ; 
    DefObject quote = new DefObject( "quote", "function", 1, "S-exp" ) ; 
    DefObject define = new DefObject( "define", "function", 29999, "S-exp" ) ;
    DefObject car = new DefObject( "car", "function", 1, "List" ) ; 
    DefObject cdr = new DefObject( "cdr", "function", 1, "List" ) ; 
    DefObject exit = new DefObject( "exit", "function", 0, "Nothing" ) ;
    DefObject ce = new DefObject( "clean-environment", "function", 0, "Nothing" ) ;
    
    DefObject add = new DefObject( "+", "function", 29999, "number" ) ;
    DefObject sub = new DefObject( "-", "function", 29999, "number" ) ;
    DefObject mult = new DefObject( "*", "function", 29999, "number" ) ;
    DefObject div = new DefObject( "/", "function", 29999, "number" ) ;
    
    DefObject isGreaterThan = new DefObject( ">", "function", 29999, "number" ) ;
    DefObject isGreaterThanOrEqual = new DefObject( ">=", "function", 29999, "number" ) ;
    DefObject isLessThan = new DefObject( "<", "function", 29999, "number" ) ;
    DefObject isLessThanOrEqual = new DefObject( "<=", "function", 29999, "number" ) ;
    DefObject isEqualTo = new DefObject( "=", "function", 29999, "number" ) ;
    
    DefObject isAtom = new DefObject( "atom?", "function", 1, "S-exp" ) ;
    DefObject isPair = new DefObject( "pair?", "function", 1, "S-exp" ) ; 
    DefObject isList = new DefObject( "list?", "function", 1, "S-exp" ) ;
    DefObject isNull = new DefObject( "null?", "function", 1, "S-exp" ) ;
    DefObject isInteger = new DefObject( "integer?", "function", 1, "S-exp" ) ;
    DefObject isReal = new DefObject( "real?", "function", 1, "S-exp" ) ;
    DefObject isNumber = new DefObject( "number?", "function", 1, "S-exp" ) ;
    DefObject isString = new DefObject( "string?", "function", 1, "S-exp" ) ;
    DefObject isBoolean = new DefObject( "boolean?", "function", 1, "S-exp" ) ;
    DefObject isSymbol = new DefObject( "symbol?", "function", 1, "S-exp" ) ;
    
    DefObject str_append = new DefObject( "string-append", "function", 29999, "string" ) ;
    DefObject str_greater = new DefObject( "string>?", "function", 29999, "string" ) ;
    DefObject str_less = new DefObject( "string<?", "function", 29999, "string" ) ;
    DefObject str_equal = new DefObject( "string=?", "function", 29999, "string" ) ;
    
    DefObject isEqv = new DefObject( "eqv?", "function", 2, "S-exp" ) ;
    DefObject isEqual = new DefObject( "equal?", "function", 2, "S-exp" ) ;
    
    DefObject not = new DefObject( "not", "function", 1, "S-exp" ) ;
    DefObject and = new DefObject( "and", "function", 29999, "S-exp" ) ;
    DefObject or = new DefObject( "or", "function", 29999, "S-exp" ) ;
    
    DefObject if_ = new DefObject( "if", "function", 23, "S-exp" ) ;
    DefObject cond = new DefObject( "cond", "function", 19999, "S-exp" ) ;
    
    DefObject begin = new DefObject( "begin", "function", 19999, "S-exp" ) ;
    
    DefObject verbose = new DefObject( "verbose", "function", 1, "S-exp" ) ;
    DefObject isVerbose = new DefObject( "verbose?", "function", 0, "S-exp" ) ;

    DefObject lambda = new DefObject( "lambda", "function", 29999, "S-exp" ) ;
    DefObject let = new DefObject( "let", "function", 29999, "S-exp" ) ;

    DefObject read = new DefObject( "read", "function", 0, "S-exp" ) ;
    DefObject write = new DefObject( "write", "function", 1, "S-exp" ) ;
    DefObject eval = new DefObject( "eval", "function", 1, "S-exp" ) ;
    DefObject set = new DefObject( "set!", "function", 2, "S-exp" ) ;
    DefObject c_error_object = new DefObject( "create-error-object", "function", 1, "string" ) ;
    DefObject is_Error_object = new DefObject( "error-object?", "function", 1, "S-exp" ) ;
    DefObject display_string = new DefObject( "display-string", "function", 1, "string_error" ) ;
    DefObject newline = new DefObject( "newline", "function", 0, "S-exp" ) ;
    DefObject symbol_to_string = new DefObject( "symbol->string", "function", 1, "symbol" ) ;
    DefObject number_to_string = new DefObject( "number->string", "function", 1, "number" ) ;

    
    def.add( number_to_string ) ;
    def.add( symbol_to_string ) ;
    def.add( newline ) ;
    def.add( display_string ) ;
    def.add( is_Error_object ) ;
    def.add( c_error_object ) ;
    def.add( set ) ;
    def.add( eval ) ;
    def.add( write ) ;
    def.add( read ) ;
    
    def.add( let ) ;
    def.add( lambda ) ;
    
    def.add( isVerbose ) ;
    def.add( verbose ) ;
    
    def.add( begin ) ;
    
    def.add( if_ ) ;
    def.add( cond ) ;
    
    def.add( or ) ;
    def.add( and ) ;
    def.add( not ) ;
    
    def.add( isEqv ) ;
    def.add( isEqual ) ;
    
    def.add( str_equal ) ;
    def.add( str_less ) ;
    def.add( str_greater ) ;
    def.add( str_append ) ;
    
    def.add( isSymbol ) ;
    def.add( isBoolean ) ;
    def.add( isString ) ;
    def.add( isNumber ) ;
    def.add( isReal ) ;
    def.add( isInteger ) ;
    def.add( isNull ) ;
    def.add( isList ) ;
    def.add( isPair ) ;
    def.add( isAtom ) ;
    
    
    def.add( isEqualTo ) ;
    def.add( isLessThanOrEqual ) ;
    def.add( isLessThan ) ;
    def.add( isGreaterThanOrEqual ) ;
    def.add( isGreaterThan ) ;
    
    def.add( div ) ;
    def.add( mult ) ;
    def.add( sub ) ;
    def.add( add ) ;
    
    def.add( ce ) ;
    def.add( exit ) ;
    def.add( cdr ) ;
    def.add( car ) ;
    def.add( define ) ;
    def.add( quote ) ;
    def.add( cons ) ;
    def.add( list ) ;
    
    return def ;
  } // InitDefObject()
  
  
  
  
  // �ѪR���Input S-Exp, �M��^�Ǥ@��Output S-Exp ( Answer )
  static Node EvalSExp( Node node, int level ) throws MyException {
    // �ݬO�_token����DefObj��name�]��O�Q�w�q�L
    // �p�G�n���O�@�L�N�q��symbol, ����haveToEqualName�n�Q�]��false, ���ݥhevaluate����binding
    // ex: ((car '(define 123)) a 5) -> ERROR (attempt to apply non-function) : define
    boolean haveToEqualName = true ;
    
    // �p�G�O�� ATOM NODE (���O�@�ʾ�)
    // �Ytype��symbol, �Y�h�䥦��binding�æ^��, �p�G�䤣��N��error msg
    // �Dsymbol�Y�����^�Ǩ�node�L����value
    if ( node.GetToken() != null ) {
      if ( IsATOM( node.GetToken() ) ) return node ;
      
      // Symbol
      else {
        DefObject temp = FindDefine( node, true ) ;
        if ( temp != null ) return temp.GetBinding() ;
        else throw new MyException( "ERROR (unbound symbol) : " +
                                    node.GetToken().PrintToken() + "\n", 8 ) ; 
      } // else
    } // if
    
    // �p�G�O�@�ʾ� (�DATOM Node)
    // 
    else {
      // �Y����`�I���O��atom node, �N�hevaluate����`�I
      if ( node.GetLeft().GetToken() == null ) {
        try {
          node.SetLeft( EvalSExp( node.GetLeft(), level + 1 ) ) ;
          haveToEqualName = false ;
        } catch ( MyException e ) {
          // �ҭn���檺function��LIST��F�ɡALIST evaluate��no return value
          if ( e.GetCase() == 88 )
            throw new MyException( "ERROR (no return value) : " + 
                                   BT.PrintLTree( node.GetLeft(), 0, false, "" ), 25 ) ;
          else throw e ;
        } // catch
      } // if
      
      
      // �Dlist  ex: (1 . 2)
      else if ( node.GetRight() != null && node.GetRight().GetToken() != null && 
                node.GetRight().GetToken().GetIntType() != Type.NIL ) 
        throw new MyException( "ERROR (non-list) : ", 11 ) ;
      
      
      // �P�_�Ĥ@�h���`�I�O���O��Function
      DefObject temp_func = FindDefine( node.GetLeft(), haveToEqualName ) ;
      
      // �O�_��attempt error�ҬO�Lformat error msg��function (define.cond.set!.let.lambda)
      boolean isFormatError_Function = false ;
      // �O�_���ݭn�P�_�h�Ū�function (define.clean-environment.exit)
      boolean isLevelError_Functuin = false ;

      if ( temp_func != null ) {
        String str = temp_func.GetBinding().GetToken().PrintToken() ;

        if ( str.equals( "#<procedure define>" ) || str.equals( "#<procedure exit>" )
             || str.equals( "#<procedure clean-environment>" ) )
          isLevelError_Functuin = true ;
        
        if ( str.equals( "#<procedure define>" ) || str.equals( "#<procedure cond>" ) || 
             str.equals( "#<procedure set!>" ) || str.equals( "#<procedure let>" ) || 
             ( str.equals( "#<procedure lambda>" ) && temp_func.GetType().equals( "function" ) ) ) 
          isFormatError_Function = true ;
      } // if
      
      
      // �t�Τ��ت�function && �ϥΪ̦ۤv�w�q��function
      if ( temp_func != null && ( temp_func.GetType().equals( "function" ) ||
                                  temp_func.GetType().equals( "function_user" ) || 
                                  temp_func.GetType().equals( "function_lambda" ) ) ) {
        
        // �p�G�ŦXFunction�W�w���ѼƭӼ�
        if ( NumberOfParameter( node.GetRight(), temp_func.GetParameterNumber() ) ) {
          try {
            return Predefined_Function( node.GetRight(), temp_func, level ) ;
          } catch ( MyException e ) {
            // �p�G���Odefine/cond/set!/let/lambda��error msg�N�~�򩹥~�h��
            if ( e.GetCase() != 87 ) throw e ;
            else {
              // define/cond/set!/let/lambda ��Error Msg : �榡���ŦX   (�n�L��ʾ�)
              throw new MyException( "ERROR (" + temp_func.mName.toUpperCase() + " format) : "
                                     + BT.PrintLTree( node, 0, false, "" ), 10 ) ;
            } // else
          } // catch
        } // if
        

        // �p�G���ŦXFunction�W�w���ѼƭӼƥBLevel���b�Ĥ@�h, �h�u����Xlevel error msg
        else if ( isLevelError_Functuin && level != 0 )
          throw new MyException( "ERROR (level of " + temp_func.mName.toUpperCase() + ")\n", 12 ) ;
        
        // �p�G���ŦXFunction�W�w���ѼƭӼ�, ��Xformat error
        // define/cond/set!/let/lambda
        else if ( isFormatError_Function )
          throw new MyException( "ERROR (" + temp_func.mName.toUpperCase() + " format) : "
                                 + BT.PrintLTree( node, 0, false, "" ), 10 ) ;
        
        // �p�G���ŦXFunction�W�w���ѼƭӼ�, ��X"�ѼƭӼƤ���"��ERROR
        // �D define/cond/set!/let/lambda
        else {
          String s = temp_func.GetBinding().GetToken().PrintToken() ;
          throw new MyException( "ERROR (incorrect number of arguments) : " 
                                 + s.substring( 12, s.length() - 1 )
                                 + "\n", 6 ) ;
        } // else
      } // if
      
      
      
      // Binding��#<procedure lambda>��function
      // �Y�Χ��Y�᪺lambda function, ex : ((lambda ( x ) ( + x 5 ))  5)
      else if ( temp_func == null && node.GetLeft().GetToken() != null && 
                node.GetLeft().GetToken().PrintToken().equals( "#<procedure lambda>" ) ) {

        if ( NumberOfParameter( node.GetRight(), Main.sLambda_Temp.GetParameterNumber() ) ) {
          try {
            return Predefined_Function( node.GetRight(), Main.sLambda_Temp, level ) ;
          } catch ( MyException e ) {
            // �p�G���Odefine/cond/set!/let/lambda��error msg�N�~�򩹥~�h��
            if ( e.GetCase() != 87 ) throw e ;
            else {
              // define/cond/set!/let/lambda ��Error Msg : �榡���ŦX   (�n�L��ʾ�)
              throw new MyException( "ERROR (LAMBDA format) : "
                                     + BT.PrintLTree( node, 0, false, "" ), 10 ) ;
            } // else
          } // catch
        } // if
        
        // �p�G���ŦXFunction�W�w���ѼƭӼƥBLevel���b�Ĥ@�h, �h�u����Xlevel error msg
        else if ( isLevelError_Functuin && level != 0 )
          throw new MyException( "ERROR (level of " + "LAMBDA" + ")\n", 12 ) ;
        
        // �p�G���ŦXFunction�W�w���ѼƭӼ�, ��Xformat error
        // define/cond/set!/let/lambda
        else if ( isFormatError_Function )
          throw new MyException( "ERROR (" + "LAMBDA" + " format) : "
                                 + BT.PrintLTree( node, 0, false, "" ), 10 ) ;
        
        // �p�G���ŦXFunction�W�w���ѼƭӼ�, ��X"�ѼƭӼƤ���"��ERROR
        // �D define/cond/set!/let/lambda
        else throw new MyException( "ERROR (incorrect number of arguments) : lambda\n", 6 ) ;
        
      } // else if
      
      
      // �L�k���Ѫ�Symbol(�W�r��Binding�Ҥ��L), ��X unbound symbol error
      else if ( temp_func == null && node.GetLeft().GetToken() != null && haveToEqualName
                && node.GetLeft().GetToken().GetIntType() == Type.SYMBOL )
        throw new MyException( "ERROR (unbound symbol) : " +
                               node.GetLeft().GetToken().PrintToken() + "\n", 8 ) ; 
      
      
      // �\�bfunction����m�B�Q�w�q�L���Otype�ëDfunction, ��error msg�򥦪�binding
      // ex : (define a 20)  ( a 5 )
      else if ( temp_func != null && temp_func.mType.equals( "symbol" ) ) {
        // System.out.println("sd") ;
        throw new MyException( "ERROR (attempt to apply non-function) : " +
                               BT.PrintLTree( temp_func.GetBinding(), 0, false, "" ), 9 ) ;
      } // else if
      
      // �Dsymbol�o�\�bfunction����m, ��X�o���Ofunction��error msg
      else {
        // System.out.println("sd") ;
        throw new MyException( "ERROR (attempt to apply non-function) : " +
                                  BT.PrintLTree( node.GetLeft(), 0, false, "" ), 9 ) ;
      } // else
    } // else
  } // EvalSExp()
  
  
  
  
  
  
  // ��M�O�_���Q�w�q�L
  // ���N�^�Ǭ۲ŦX��DefObject,�S���N�^��null
  static DefObject FindDefine( Node node, boolean equalName ) {
    // �O�ʾ�
    if ( node.GetToken() == null ) return null ;
    
    String name = node.GetToken().PrintToken() ;
    
    // �p�G�O����Let Function �B ���w�q�ϰ��ܼ�
    if ( Main.sIsLetFunction && Main.sLocal_Def != null && ! Main.sLocal_Def.isEmpty() ) {
      
      for ( int i = Main.sLocal_Def.size() - 1 ; i > - 1 ; i-- ) {
        for ( int j = 0 ; j < Main.sLocal_Def.get( i ).GetVec().size() ; j++ ) {
          
          if ( ! equalName ) {
            if ( node == Main.sLocal_Def.get( i ).GetVec().get( j ).GetBinding() )
              return Main.sLocal_Def.get( i ).GetVec().get( j ) ;
          } // if
          
          else {
            if ( name.equals( Main.sLocal_Def.get( i ).GetVec().get( j ).GetName() ) ||
                 node == Main.sLocal_Def.get( i ).GetVec().get( j ).GetBinding() )
              return Main.sLocal_Def.get( i ).GetVec().get( j ) ;
          } // else
          
        } // for
      } // for
    } // if ______����Let Function �B ���w�q�ϰ��ܼ�______
    
    
    
    // �p�G���O����Let Function �B ���w�q�Ѽ� 
    // lambda �� define�X�Ӫ��ϥΪ̦۩w�qfunction, �p�G�ܼƦb�ѼƤ��S�����w�q, �N�h�����
    // �o��O�u�b�ѼƧ�  ( stack�̷spush�i�Ӫ��@�h )
    else if ( ! Main.sIsLetFunction && Main.sLocal_Def != null && ! Main.sLocal_Def.isEmpty() ) {
      for ( int j = 0 ; j < Main.sLocal_Def.lastElement().GetVec().size() ; j++ ) {
        
        if ( ! equalName ) {
          if ( node == Main.sLocal_Def.lastElement().GetVec().get( j ).GetBinding() )
            return Main.sLocal_Def.lastElement().GetVec().get( j ) ;
        } // if
        
        else {
          if ( name.equals( Main.sLocal_Def.lastElement().GetVec().get( j ).GetName() ) ||
               node == Main.sLocal_Def.lastElement().GetVec().get( j ).GetBinding() )
            return Main.sLocal_Def.lastElement().GetVec().get( j ) ;
        } // else
        
      } // for
      
    } // else if ______���O����Let Function �B ���w�q�Ѽ� ______
    
    
    // �w�q�L�������ܼ�
    for ( int i = 0 ; i < Main.sVec_Def.size() ; i++ ) {
      
      if ( ! equalName ) {
        if ( node == Main.sVec_Def.get( i ).GetBinding() )
          return Main.sVec_Def.get( i ) ;
      } // if
      
      else {
        if ( name.equals( Main.sVec_Def.get( i ).GetName() ) ||
             node == Main.sVec_Def.get( i ).GetBinding() )
          return Main.sVec_Def.get( i ) ;
      } // else
      
    } // for
    
    return null ;
  } // FindDefine()
  
  
  
  
  
  // �P�_�O�_�ŦXFunction�ҩw�q���ѼƭӼ�
  static Boolean NumberOfParameter( Node node, int num ) throws MyException {
    int return_num = CountParameter( node ) ;
    
    // �ѼƭӼ� : 0 ~ �L�W��
    if ( num == 9999 ) return true ;
    
    // �ѼƭӼ� : 1 ~ �L�W��
    else if ( num == 19999 ) {
      if ( return_num >= 1 ) return true ;
      else return false ;
    } // else if
    
    // �ѼƭӼ� : 2 ~ �L�W��
    else if ( num == 29999 ) {
      if ( return_num >= 2 ) return true ;
      else return false ;
    } // else if
    
    // �ѼƭӼ� : 2 ��  3
    else if ( num == 23 ) {
      if ( return_num == 2 || return_num == 3 ) return true ;
      else return false ;
    } // else if
    
    // �ѼƦ��W�w�T�w���ƶq
    else {
      if ( return_num == num ) return true ;
      else return false ;
    } // else
  } // NumberOfParameter()
  
  
  
  // �p��ѼƭӼ�
  static int CountParameter( Node node ) throws MyException {
    if ( node == null ) return 0 ;
    else if ( node.GetToken() != null && node.GetToken().GetIntType() == Type.NIL )
      return 0 ;
    else if ( node.GetRight() == null ) return 1 ;
    else if ( node.GetRight().GetToken() != null && 
              node.GetRight().GetToken().GetIntType() == Type.NIL ) return 1 ;
    else return CountParameter( node.GetRight() ) + 1 ;
  } // CountParameter()
  
  
  // �P�_�O�_�����FSymbol�~��ATOM
  static boolean IsATOM( Token t ) {
    int type = t.GetIntType() ;
    if ( type == Type.INT || type == Type.FLOAT || type == Type.STRING ||
         type == Type.NIL || type == Type.T ) {
      return true ;
    } // if
    else return false ;
  } // IsATOM()
  
  
  
  // Evaluate�C�ӰѼ�,�M��s��@��vector�̭�
  static Vector<Node> GetParameter( Node node, int level ) throws MyException {
    Vector<Node> vec = new Vector<Node>() ;
    while ( node != null ) {
      if ( node.GetToken() != null && node.GetToken().GetIntType() == Type.NIL ) ;
      
      // �k�`�I�O��ATOM NODE
      else if ( node.GetToken() != null ) throw new MyException( "ERROR (non-list) : ", 11 ) ;
      
      else {
        try {
          Node temp = EvalSExp( node.GetLeft(), level + 1 ) ;
          vec.add( temp ) ;
        } catch ( MyException e ) {
          // �Ѽ�evaluate�� no return value
          if ( e.GetCase() == 88 ) {
            throw new MyException( "ERROR (unbound parameter) : " + 
                                   BT.PrintLTree( node.GetLeft(), 0, false, "" ), 20 ) ;
          } // if
          else throw e ;
        } // catch
      } // else
      
      node = node.GetRight() ;
    } // while
    
    return vec ;
  } // GetParameter()
  
  
  
  // �P�_Parameter��Type
  static String ParameterType( Node node ) {
    if ( node.GetToken() != null ) {
      if ( node.GetToken().GetIntType() == Type.INT )
        return "integer" ; 
      else if ( node.GetToken().GetIntType() == Type.FLOAT )
        return "float" ;
      else if ( node.GetToken().GetIntType() == Type.STRING )
        return "string" ;
      else if ( node.GetToken().GetIntType() == Type.T )
        return "#t" ;
      else if ( node.GetToken().GetIntType() == Type.NIL )
        return "nil" ;
      else if ( node.GetToken().GetIntType() == Type.ERROR )
        return "error" ;
      else return "symbol" ;
    } // if
    
    else return "list" ;
  } // ParameterType()
  
  

  // �ݦ�list�O���Opure list(�̥k�䬰nil)
  static boolean IsPureList( Node node ) {

    if ( node != null && node.GetLeft() == null && node.GetRight() == null
         && node.GetToken() != null ) return true ;
    else if ( node != null && node.GetLeft() == null && node.GetRight() != null
              && node.GetRight().GetToken().GetIntType() == Type.NIL
              && node.GetToken() != null ) return true ;
    
    while ( node != null ) {  
      // ��̧ܳ���(����)
      if ( node.GetToken() != null && node.GetToken().GetIntType() == Type.NIL ) ;
      
      // �k�`�I�O��ATOM NODE
      else if ( node != null && node.GetToken() != null ) return false ;
      
      node = node.GetRight() ;
    } // while
    
    return true ;
  } // IsPureList()
  
  
  // �P�_�O���بt�Τ���Function, �M��h�I�s����Function
  static Node Predefined_Function( Node node, DefObject def, int level ) throws MyException {
    Vector<Node> parameter = new Vector<Node>() ;
    String str = def.GetBinding().GetToken().PrintToken() ;
    
    // �p�G�Odefine function, ������evaluate�Ѽ�
    if ( str.equals( "#<procedure define>" ) ) {
      if ( level == 0 ) {
        MyFunctions.Define( node ) ;
        return null ;
      } // if
      
      else throw new MyException( "ERROR (level of DEFINE)\n", 12 ) ;
    } // if
    
    else if ( str.equals( "#<procedure quote>" ) ) return node.GetLeft() ;
    
    else if ( str.equals( "#<procedure clean-environment>" ) ) {
      if ( level == 0 ) {
        MyFunctions.Clean_Environment() ;
        return null ;
      } // if
      else throw new MyException( "ERROR (level of CLEAN-ENVIRONMENT)\n", 12 ) ;
    } // else if
    
    else if ( str.equals( "#<procedure exit>" ) ) {
      if ( level == 0 ) throw new MyException( "", 3 ) ;  // ����Main, �����{��
      else throw new MyException( "ERROR (level of EXIT)\n", 12 ) ;
    } // else if
    
    else if ( str.equals( "#<procedure cond>" ) ) return MyFunctions.Cond( node ) ;
    else if ( str.equals( "#<procedure begin>" ) ) return MyFunctions.Begin( node ) ;
    else if ( str.equals( "#<procedure and>" ) ) return MyFunctions.And( node ) ;
    else if ( str.equals( "#<procedure or>" ) ) return MyFunctions.Or( node ) ;
    else if ( str.equals( "#<procedure if>" ) ) return MyFunctions.If( node ) ;
    else if ( str.equals( "#<procedure verbose?>" ) ) return MyFunctions.IsVerbose() ;
    
    else if ( str.equals( "#<procedure lambda>" ) && def.GetType().equals( "function" ) )
      return MyFunctions.Lambda( node ) ;
    
    else if ( str.equals( "#<procedure lambda>" ) && def.GetType().equals( "function_lambda" ) )
      return MyFunctions.User_Function( node, def ) ;
    
    // �p�G�OLet Function�N���w�q�ϰ��ܼ�, ���ܼƩw�q�ɭn�iStack�@�h�@�h��
    else if ( str.equals( "#<procedure let>" ) ) {
      Main.sIsLetFunction = true ;
      return MyFunctions.Let( node ) ;
    } // else if
    
    // ---------------- project 4 ----------------
    else if ( str.equals( "#<procedure set!>" ) ) {
      return MyFunctions.Set( node ) ;
    } // else if

    // ex : #<procedure F> ... ��
    // ���F�קK(read)�hŪ�@���Ѽ�, �G���b�U����evaluate�Ѽƪ�else block��call function
    else if ( def.GetType().equals( "function_lambda" ) ) {
      return MyFunctions.User_Function( node, def ) ;
    } // else if 
    // ---------------- project 4 ----------------
    
    
    else {  // �ѼƳ��i�H��evaluate��functions
      parameter = GetParameter( node, level ) ;
      if ( str.equals( "#<procedure cons>" ) ) return MyFunctions.Cons( parameter ) ;
      else if ( str.equals( "#<procedure list>" ) ) return MyFunctions.List( parameter ) ;
      else if ( str.equals( "#<procedure car>" ) ) return MyFunctions.Car( parameter ) ;
      else if ( str.equals( "#<procedure cdr>" ) ) return MyFunctions.Cdr( parameter ) ;
      
      else if ( str.equals( "#<procedure +>" ) ) return MyFunctions.Add( parameter ) ;
      else if ( str.equals( "#<procedure ->" ) ) return MyFunctions.Sub( parameter ) ;
      else if ( str.equals( "#<procedure *>" ) ) return MyFunctions.Mult( parameter ) ;
      else if ( str.equals( "#<procedure />" ) ) return MyFunctions.Div( parameter ) ;
      else if ( str.equals( "#<procedure >>" ) ) return MyFunctions.IsGreaterThan( parameter ) ;
      else if ( str.equals( "#<procedure >=>" ) ) return MyFunctions.IsGreaterThanOrEqual( parameter ) ;
      else if ( str.equals( "#<procedure <>" ) ) return MyFunctions.IsLessThan( parameter ) ;
      else if ( str.equals( "#<procedure <=>" ) ) return MyFunctions.IsLessThanOrEqual( parameter ) ;
      else if ( str.equals( "#<procedure =>" ) ) return MyFunctions.IsEqualTo( parameter ) ;
      
      else if ( str.equals( "#<procedure atom?>" ) ) return MyFunctions.IsAtom( parameter ) ;
      else if ( str.equals( "#<procedure pair?>" ) ) return MyFunctions.IsPair( parameter ) ;
      else if ( str.equals( "#<procedure list?>" ) ) return MyFunctions.IsList( parameter ) ;
      else if ( str.equals( "#<procedure null?>" ) ) return MyFunctions.IsNull( parameter ) ;
      else if ( str.equals( "#<procedure integer?>" ) ) return MyFunctions.IsInteger( parameter ) ;
      else if ( str.equals( "#<procedure real?>" ) ) return MyFunctions.IsReal_or_Number( parameter ) ;
      else if ( str.equals( "#<procedure number?>" ) ) return MyFunctions.IsReal_or_Number( parameter ) ;
      else if ( str.equals( "#<procedure string?>" ) ) return MyFunctions.IsString( parameter ) ;
      else if ( str.equals( "#<procedure boolean?>" ) ) return MyFunctions.IsBoolean( parameter ) ;
      else if ( str.equals( "#<procedure symbol?>" ) ) return MyFunctions.IsSymbol( parameter ) ;
      
      else if ( str.equals( "#<procedure string-append>" ) ) return MyFunctions.Str_Append( parameter ) ;
      else if ( str.equals( "#<procedure string>?>" ) ) return MyFunctions.Str_Greater( parameter ) ;
      else if ( str.equals( "#<procedure string<?>" ) ) return MyFunctions.Str_Less( parameter ) ;
      else if ( str.equals( "#<procedure string=?>" ) ) return MyFunctions.Str_Equal( parameter ) ;
      
      else if ( str.equals( "#<procedure eqv?>" ) ) return MyFunctions.IsEquivalent( parameter ) ;
      else if ( str.equals( "#<procedure equal?>" ) ) return MyFunctions.IsEqual( parameter ) ;
      else if ( str.equals( "#<procedure not>" ) ) return MyFunctions.Not( parameter ) ; 
      
      else if ( str.equals( "#<procedure verbose>" ) ) return MyFunctions.Verbose( parameter ) ;

      // ---------------- project 4 ----------------
      else if ( str.equals( "#<procedure read>" ) ) return MyFunctions.Read() ;
      else if ( str.equals( "#<procedure write>" ) ) return MyFunctions.Write( parameter ) ;
      else if ( str.equals( "#<procedure eval>" ) ) return MyFunctions.Eval( parameter ) ;
      else if ( str.equals( "#<procedure newline>" ) ) return MyFunctions.NewLine() ;
      
      else if ( str.equals( "#<procedure create-error-object>" ) )
        return MyFunctions.C_Error_Object( parameter ) ;
      
      else if ( str.equals( "#<procedure error-object?>" ) )
        return MyFunctions.IsErrorObject( parameter ) ;
      
      else if ( str.equals( "#<procedure display-string>" ) )
        return MyFunctions.Display_String( parameter ) ;
      
      else if ( str.equals( "#<procedure symbol->string>" ) )
        return MyFunctions.SymbolToString( parameter ) ;
      
      else if ( str.equals( "#<procedure number->string>" ) )
        return MyFunctions.NumberToString( parameter ) ;
      // ---------------- project 4 ----------------
      
      else {       // ��BUG��
        Token t = new Token( "Predefined_Function_test", 0, 0 ) ;
        t.Classify();
        return new Node( t ) ;
      } // else    // ��BUG��
    } // else
    
  } // Predefined_Function()
  
} // class Evaluate