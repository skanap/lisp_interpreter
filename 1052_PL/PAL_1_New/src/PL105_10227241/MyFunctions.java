package PL105_10227241;

import java.util.Vector;

class MyFunctions {
  
  // -------------------------------------------------------------------------------
  // Function Name : cons
  // рㄢ把计挡Θdotted pair
  static Node Cons( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    ans.SetLeft( vec.get( 0 ) );
    ans.SetRight( vec.get( 1 ) );
    return ans ;
  } // Cons()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : list
  // р┮Τ把计挡Θlist
  static Node List( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    Node head = new Node() ;
    ans = head ;
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      head.SetLeft( vec.get( i ) );
      Node temp = new Node() ;
      head.SetRight( temp ) ;
      if ( i != vec.size()-1 ) head = head.GetRight() ;  // 狦琌程把计碞ぃ璶┕糷禲
    } // for
    
    Node nu = null ;           // 浪琩璶―
    head.SetRight( nu ) ;  // р程糷竊翴砞null
    
    // 把计计秖0, 肚nil
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
  // ﹚竡把计種竡, parameter1﹚竡parameter2
  static void Define( Node node ) throws MyException {
    // _______________________________ 跑计跋 _______________________________
    
    Node para1 = node.GetLeft() ;              // 把计1, 砆﹚竡symbol
    Node para2 = node.GetRight().GetLeft() ;   // 把计2, Binding (value)
                                                                                
    DefObject def1 = null, def2 = null, new_def = null ;                        
    Boolean para1_IsDefined = false ;      // 把计1Τ⊿Τ砆﹚竡筁 (sVec_Defぃ把计1)
    
    int para_num = 0 ;                                    // define function ノ
    Vector<DefObject> f_para = new Vector<DefObject>() ;  // define function ノ
    Vector<Node> f_body = new Vector<Node>() ;            // define function ノ
    
    // _______________________________ 跑计跋 _______________________________
    
    
    // _______________________________ 矪瞶把计1 _______________________________
    
    // 把计1琌Symbol
    if ( para1.GetToken() != null && para1.GetToken().GetIntType() == Type.SYMBOL ) {
      // 狦璶﹚竡symbolbinding, define钡ㄢ把计(symbol name & binding)
      // 璝Τㄢ把计メerror msg
      if ( node.GetRight().GetRight() != null && node.GetRight().GetRight().GetToken() == null
           && node.GetRight().GetRight().GetLeft() != null )
        throw new MyException( "", 87 ) ;

      def1 = Evaluate.FindDefine( para1, true ) ;    // 碝т琌砆﹚竡筁, ┪Binding单
    } // if


    // 把计1琌List, 斗才﹚竡FunctionΑ
    // (define ( ... ) ......) // ﹚竡function
    // =================================================================
    else if ( Evaluate.ParameterType( para1 ).equals( "list" ) ) {
      if ( para1.GetLeft().GetToken().GetIntType() != Type.SYMBOL ) 
        throw new MyException( "", 87 ) ;
      
      else {
        // 眔 function name
        String f_name = para1.GetLeft().GetToken().PrintToken() ;
        DefObject def_func = Evaluate.FindDefine( new Node( new Token( f_name, 0, 0 ) ),
                                                  true ) ;
        // 琌╰参ずfunction, 珿ぃ砆狡﹚竡 -> メerror msg
        if ( def_func != null && def_func.mType.equals( "function" ) )
          throw new MyException( "", 87 ) ;
        
        // 砆﹚竡筁
        else if ( def_func != null ) {
          Main.sVec_Def.remove( def_func ) ;   // para1_IsDefined = true ;
        } // else if
        
        // ⊿砆﹚竡筁
        else ;
        
        
        // ------ 眔 ( function_name  { function_para } ) ------ 
        para1 = para1.GetRight() ;
        while ( para1 != null ) {
          if ( para1.GetToken() != null && para1.GetToken().GetIntType() == Type.NIL ) ;
          
          // 竊翴琌ATOM NODE
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
        // ------ 眔 ( function_name  { function_para } ) ------
        
        
        // ------------------ 眔 function body ------------------
        Node para_f_body = node.GetRight() ;
        
        while ( para_f_body != null ) {
          if ( ! Evaluate.IsPureList( para_f_body.GetLeft() ) ) throw new MyException( "", 87 ) ;
          
          if ( para_f_body.GetToken() != null && para_f_body.GetToken().GetIntType() == Type.NIL ) ;
          
          // 竊翴琌ATOM NODE
          else if ( para_f_body.GetToken() != null ) throw new MyException( "ERROR (non-list) : ", 11 ) ;
          
          else f_body.add( para_f_body.GetLeft() ) ;
          
          para_f_body = para_f_body.GetRight() ;
        } // while
        // ------------------ 眔 function body ------------------
        
        
        new_def = new DefObject( f_name, "function_lambda",
                                           new Node( new Token( "#<procedure " + f_name  + ">", 0, 0 ) ),
                                           para_num, "s-exp", f_para, f_body ) ;
        
        Main.sVec_Def.add( new_def ) ;
        
        if ( Main.sIsVerbose )     // verbose mode秨币, 璶传︽
          System.out.println( f_name + " defined" );
      } // else
      
      return ;
    } // else if 把计1 琌 list, 斗才﹚竡FunctionΑ
    // =================================================================

    // define format error
    else throw new MyException( "", 87 ) ;
    
    
    // ------------------- 把计1Τ⊿Τ砆﹚竡筁 -------------------
    // 砆﹚竡筁獶ずfunction
    if ( def1 != null && ! def1.GetType().equals( "function" ) ) {
      para1_IsDefined = true ;
      // 癘帝Τ砆﹚竡筁,临ぃremove奔,狦把计2Τノセ﹚竡碞тぃセ﹚竡
      // Main.sVec_Def.remove( def1 ) ;
    } // if

    // 砆﹚竡筁ずfunction
    else if ( def1 != null && def1.GetType().equals( "function" ) )
      // 把计1╰参ずFunction
      throw new MyException( "", 87 ) ;
    
    // def1 == null
    else ;
    // ------------------- 把计1Τ⊿Τ砆﹚竡筁 -------------------
    
    // _______________________________ 矪瞶把计1 _______________________________
    
    
    // _______________________________ 矪瞶把计2 _______________________________
    
    Node para2_eval = new Node() ;
    try {
      para2_eval = Evaluate.EvalSExp( para2, 1 ) ;
    } catch ( MyException e ) {
      // 璶﹚竡binding evaluateЧ No return value -> error
      if ( e.GetCase() == 88 ) 
        throw new MyException( "ERROR (no return value) : " + 
                               BT.PrintLTree( para2, 0, false, "" ), 23 ) ;
      else throw e ;
    } // catch
    
    if ( para2_eval.GetToken() != null ) {
      // System.out.println(para2_eval.GetToken().PrintToken() + para2_eval.GetToken().GetStringType() ) ;
      def2 = Evaluate.FindDefine( para2_eval, false ) ;
      // 矪瞶 (define x (quote a)) , ┮FindDefine把计肚false
      // 璶binding蛤token妓衡砆﹚竡筁, 蛤token妓 ぃノтウbinding
      //  'a 碞钩 'cons 妓, 碞衡 cons 癬ㄓ琌Τ﹚竡筁, ㄆ龟ぃ琌 binding  #<procedure cons>
    } // if
      
    // 硂奔セ﹚竡, 把计2砆evaluate
    if ( para1_IsDefined ) Main.sVec_Def.remove( def1 ) ;
    
    // 把计2琌埃symbolatom node (int,float,string,#t,nil)
    // ぃ惠璶恨def2Τ⊿Τт, ぃ礛 (define a 5 ) (define b a ) 硂case def2獶null
    // 钡binding
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
    
    // 把计2琌竒筁quote矪瞶筁symbol atom node
    // ex : (define a 'hi)
    else if ( para2_eval.GetToken() != null && def2 == null
              && para2_eval.GetToken().GetIntType() == Type.SYMBOL )
      new_def = new DefObject( para1.GetToken().PrintToken(), "symbol", para2_eval ) ;
    
    // 把计2琌獶ATOM Node  ⊿砆﹚竡筁  (  : (1 . 2) )
    // 钡binding
    else if ( para2_eval.GetToken() == null && def2 == null )
      new_def = new DefObject( para1.GetToken().PrintToken(), "symbol", para2_eval ) ;
    
    // 把计2琌砆﹚竡筁function
    // 钡binding (defObjtypefunction_user)
    else if ( def2 != null && ( def2.GetType().equals( "function" ) || 
                                def2.GetType().equals( "function_user" ) ) ) {
      new_def = new DefObject( para1.GetToken().PrintToken(), "function_user", 
                               def2.GetParameterNumber(), def2.GetParameterType() ) ;
      // System.out.println( def2.GetBinding().GetToken().PrintToken() ) ;
      new_def.SetBinding( def2.GetBinding() ) ;
    } // else if
    
    
    // 把计2琌砆﹚竡筁functionㄏノ﹚竡function
    // 钡binding (defObjtypefunction_lambda)
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
    
    // _______________________________ 矪瞶把计2 _______________________________
    
    
    // _______________________________ 矪瞶肚  _______________________________
    
    Main.sVec_Def.add( new_def ) ;
    
    if ( Main.sIsVerbose )      // verbose mode秨币, 璶传︽
      System.out.println( para1.GetToken().PrintToken() + " defined" ) ;
    
    return ;
    // _______________________________ 矪瞶肚  _______________________________

  } // Define()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : car
  // List材Element
  static Node Car( Vector<Node> vec ) throws MyException {
    if ( Evaluate.ParameterType( vec.get( 0 ) ).equals( "list" ) )
      return vec.get( 0 ).GetLeft() ;   // 秈Vector竒Evaluate筁,珿ぃゲEvaluate
    else throw new MyException( "ERROR (car with incorrect argument type) : "
                                + BT.PrintLTree( vec.get( 0 ), 0, false, "" ), 7 ) ;
  } // Car()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : cdr
  // List埃材,逞Element
  static Node Cdr( Vector<Node> vec ) throws MyException {
    if ( Evaluate.ParameterType( vec.get( 0 ) ).equals( "list" ) ) {
      
      // 狦娩竊翴null, 玥рウэnilatom node
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
  // 睲埃ㄏノ﹚竡Symbol┪Function
  static void Clean_Environment() {
    Main.sVec_Def.clear() ;
    Main.sVec_Def = Evaluate.InitDefObject() ;
    if ( Main.sIsVerbose ) System.out.print( "environment cleaned" ) ;
    System.out.println() ;  // ぃ阶verbose mode琌秨币, 常璶传︽
  } // Clean_Environment()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : +
  // return┮Τ把计羆挡狦
  static Node Add( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    int i_ans = 0 ;
    double f_ans = 0 ;
    boolean allInteger = true ;
    
    // 耞把计
    // 璝ぃ琌integerぃ琌float, 碞璶メError Message
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      if ( Evaluate.ParameterType( vec.get( i ) ).equals( "integer" ) ) ;
      else if ( Evaluate.ParameterType( vec.get( i ) ).equals( "float" ) )
        allInteger = false ;
      // 獶计把计
      else throw new MyException( "ERROR (+ with incorrect argument type) : "
                                  + BT.PrintLTree( vec.get( i ), 0, false, "" ), 7 ) ;
    } // for
    
    
    // 狦场把计常琌Integer
    if ( allInteger ) {
      for ( int i = 0 ; i < vec.size() ; i++ ) {
        i_ans = i_ans + Integer.parseInt( vec.get( i ).GetToken().PrintToken() ) ;
      } // for
      
      Token t = new Token( Integer.toString( i_ans ), 0, 0 ) ;
      t.Classify() ;
      ans.SetToken( t ) ;
    } // if
    
    
    // ㄤいぶΤ把计琌Float
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
  // return┮Τ把计眖オ搭挡狦
  static Node Sub( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    int i_ans = 0 ;
    double f_ans = 0 ;
    boolean allInteger = true ;
    
    // 耞把计
    // 璝ぃ琌integerぃ琌float, 碞璶メError Message
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      if ( Evaluate.ParameterType( vec.get( i ) ).equals( "integer" ) ) ;
      else if ( Evaluate.ParameterType( vec.get( i ) ).equals( "float" ) )
        allInteger = false ;
      // 獶计把计
      else throw new MyException( "ERROR (- with incorrect argument type) : "
                                  + BT.PrintLTree( vec.get( i ), 0, false, "" ), 7 ) ;
    } // for
    
    
    // 狦场把计常琌Integer
    if ( allInteger ) {
      // 材把计﹍, 礛搭奔ぇ把计
      i_ans = Integer.parseInt( vec.get( 0 ).GetToken().PrintToken() ) ;
      
      // **** i眖1秨﹍
      for ( int i = 1 ; i < vec.size() ; i++ ) {
        i_ans = i_ans - Integer.parseInt( vec.get( i ).GetToken().PrintToken() ) ;
      } // for
      
      Token t = new Token( Integer.toString( i_ans ), 0, 0 ) ;
      t.Classify() ;
      ans.SetToken( t ) ;
    } // if
    
    
    // ㄤいぶΤ把计琌Float
    else {
      // 材把计﹍, 礛搭奔ぇ把计
      f_ans = Double.parseDouble( vec.get( 0 ).GetToken().PrintToken() ) ;
      
      // **** i眖1秨﹍
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
  // return┮Τ把计眖オ挡狦
  static Node Mult( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    int i_ans = 0 ;
    double f_ans = 0 ;
    boolean allInteger = true ;
    
    // 耞把计
    // 璝ぃ琌integerぃ琌float, 碞璶メError Message
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      if ( Evaluate.ParameterType( vec.get( i ) ).equals( "integer" ) ) ;
      else if ( Evaluate.ParameterType( vec.get( i ) ).equals( "float" ) )
        allInteger = false ;
      // 獶计把计
      else throw new MyException( "ERROR (* with incorrect argument type) : "
                                  + BT.PrintLTree( vec.get( i ), 0, false, "" ), 7 ) ;
    } // for
    
    
    // 狦场把计常琌Integer
    if ( allInteger ) {
      // 材把计﹍, 礛ぇ把计
      i_ans = Integer.parseInt( vec.get( 0 ).GetToken().PrintToken() ) ;
      
      // **** i眖1秨﹍
      for ( int i = 1 ; i < vec.size() ; i++ ) {
        i_ans = i_ans * Integer.parseInt( vec.get( i ).GetToken().PrintToken() ) ;
      } // for
      
      Token t = new Token( Integer.toString( i_ans ), 0, 0 ) ;
      t.Classify() ;
      ans.SetToken( t ) ;
    } // if
    
    
    // ㄤいぶΤ把计琌Float
    else {
      // 材把计﹍, 礛ぇ把计
      f_ans = Double.parseDouble( vec.get( 0 ).GetToken().PrintToken() ) ;
      
      // **** i眖1秨﹍
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
  // return┮Τ把计眖オ埃挡狦, 璝埃计0玥メError Message
  static Node Div( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    int i_ans = 0 ;
    double f_ans = 0 ;
    boolean allInteger = true ;
    
    // 耞把计
    // 璝ぃ琌integerぃ琌float, 碞璶メError Message
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      if ( Evaluate.ParameterType( vec.get( i ) ).equals( "integer" ) ) ;
      else if ( Evaluate.ParameterType( vec.get( i ) ).equals( "float" ) )
        allInteger = false ;
      // 獶计把计
      else throw new MyException( "ERROR (/ with incorrect argument type) : "
                                  + BT.PrintLTree( vec.get( i ), 0, false, "" ), 7 ) ;
    } // for
    
    
    // 狦场把计常琌Integer
    if ( allInteger ) {
      // 材把计﹍, 礛埃ぇ把计
      i_ans = Integer.parseInt( vec.get( 0 ).GetToken().PrintToken() ) ;
      
      // **** i眖1秨﹍
      // 浪琩埃计(把计)琌0, 璝0玥メError Message
      for ( int i = 1 ; i < vec.size() ; i++ ) {
        int i_par = Integer.parseInt( vec.get( i ).GetToken().PrintToken() ) ;
        if ( i_par == 0 ) throw new MyException( "ERROR (division by zero) : /\n", 13 ) ;
        else i_ans = i_ans / Integer.parseInt( vec.get( i ).GetToken().PrintToken() ) ;
      } // for
      
      Token t = new Token( Integer.toString( i_ans ), 0, 0 ) ;
      t.Classify() ;
      ans.SetToken( t ) ;
    } // if
    
    
    // ㄤいぶΤ把计琌Float
    else {
      // 材把计﹍, 礛埃ぇ把计
      f_ans = Double.parseDouble( vec.get( 0 ).GetToken().PrintToken() ) ;
      
      // **** i眖1秨﹍
      // 浪琩埃计(把计)琌0, 璝0玥メError Message
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
  // 耞┮Τ把计琌常ウ把计, 琌杠return #t (true), 玥return nil (false)
  static Node IsGreaterThan( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    int i_cmp = 0 ;        // ゑ耕膀非
    double f_cmp = 0 ;     // ゑ耕膀非
    boolean allInteger = true, allTrue = true ;
    
    // 耞把计
    // 璝ぃ琌integerぃ琌float, 碞璶メError Message
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      if ( Evaluate.ParameterType( vec.get( i ) ).equals( "integer" ) ) ;
      else if ( Evaluate.ParameterType( vec.get( i ) ).equals( "float" ) )
        allInteger = false ;
      // 獶计把计
      else throw new MyException( "ERROR (> with incorrect argument type) : "
                                  + BT.PrintLTree( vec.get( i ), 0, false, "" ), 7 ) ;
    } // for
    
    
    // 狦场把计常琌Integer
    if ( allInteger ) {
      // 材把计秨﹍ゑ耕膀非, 礛籔ぇ把计ゑ耕
      i_cmp = Integer.parseInt( vec.get( 0 ).GetToken().PrintToken() ) ;
      
      // **** i眖1秨﹍
      for ( int i = 1 ; i < vec.size() && allTrue ; i++ ) {
        int i_par = Integer.parseInt( vec.get( i ).GetToken().PrintToken() ) ;
        if ( i_cmp > i_par ) i_cmp = i_par ;  // True, р膀非砞把计
        else allTrue = false ;
      } // for
    } // if
    
    
    // ㄤいぶΤ把计琌Float
    else {
      // 材把计秨﹍ゑ耕膀非, 礛籔ぇ把计ゑ耕
      f_cmp = Double.parseDouble( vec.get( 0 ).GetToken().PrintToken() ) ;
      
      // **** i眖1秨﹍
      for ( int i = 1 ; i < vec.size() && allTrue ; i++ ) {
        double f_par = Double.parseDouble( vec.get( i ).GetToken().PrintToken() ) ;
        if ( f_cmp > f_par ) f_cmp = f_par ;  // True, р膀非砞把计
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
  // 耞┮Τ把计琌常┪单ウ把计, 琌杠return #t (true), 玥return nil (false)
  static Node IsGreaterThanOrEqual( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    int i_cmp = 0 ;       // ゑ耕膀非
    double f_cmp = 0 ;     // ゑ耕膀非
    boolean allInteger = true, allTrue = true ;
    
    // 耞把计
    // 璝ぃ琌integerぃ琌float, 碞璶メError Message
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      if ( Evaluate.ParameterType( vec.get( i ) ).equals( "integer" ) ) ;
      else if ( Evaluate.ParameterType( vec.get( i ) ).equals( "float" ) )
        allInteger = false ;
      // 獶计把计
      else throw new MyException( "ERROR (>= with incorrect argument type) : "
                                  + BT.PrintLTree( vec.get( i ), 0, false, "" ), 7 ) ;
    } // for
    
    
    // 狦场把计常琌Integer
    if ( allInteger ) {
      // 材把计秨﹍ゑ耕膀非, 礛籔ぇ把计ゑ耕
      i_cmp = Integer.parseInt( vec.get( 0 ).GetToken().PrintToken() ) ;
      
      // **** i眖1秨﹍
      for ( int i = 1 ; i < vec.size() && allTrue ; i++ ) {
        int i_par = Integer.parseInt( vec.get( i ).GetToken().PrintToken() ) ;
        if ( i_cmp >= i_par ) i_cmp = i_par ;  // True, р膀非砞把计
        else allTrue = false ;
      } // for
    } // if
    
    
    // ㄤいぶΤ把计琌Float
    else {
      // 材把计秨﹍ゑ耕膀非, 礛籔ぇ把计ゑ耕
      f_cmp = Double.parseDouble( vec.get( 0 ).GetToken().PrintToken() ) ;
      
      // **** i眖1秨﹍
      for ( int i = 1 ; i < vec.size() && allTrue ; i++ ) {
        double f_par = Double.parseDouble( vec.get( i ).GetToken().PrintToken() ) ;
        if ( f_cmp >= f_par ) f_cmp = f_par ;  // True, р膀非砞把计
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
  // 耞┮Τ把计琌常ウ把计, 琌杠return #t (true), 玥return nil (false)
  static Node IsLessThan( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    int i_cmp = 0 ;       // ゑ耕膀非
    double f_cmp = 0 ;     // ゑ耕膀非
    boolean allInteger = true, allTrue = true ;
    
    // 耞把计
    // 璝ぃ琌integerぃ琌float, 碞璶メError Message
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      if ( Evaluate.ParameterType( vec.get( i ) ).equals( "integer" ) ) ;
      else if ( Evaluate.ParameterType( vec.get( i ) ).equals( "float" ) )
        allInteger = false ;
      // 獶计把计
      else throw new MyException( "ERROR (< with incorrect argument type) : "
                                  + BT.PrintLTree( vec.get( i ), 0, false, "" ), 7 ) ;
    } // for
    
    
    // 狦场把计常琌Integer
    if ( allInteger ) {
      // 材把计秨﹍ゑ耕膀非, 礛籔ぇ把计ゑ耕
      i_cmp = Integer.parseInt( vec.get( 0 ).GetToken().PrintToken() ) ;
      
      // **** i眖1秨﹍
      for ( int i = 1 ; i < vec.size() && allTrue ; i++ ) {
        int i_par = Integer.parseInt( vec.get( i ).GetToken().PrintToken() ) ;
        if ( i_cmp < i_par ) i_cmp = i_par ;  // True, р膀非砞把计
        else allTrue = false ;
      } // for
    } // if
    
    
    // ㄤいぶΤ把计琌Float
    else {
      // 材把计秨﹍ゑ耕膀非, 礛籔ぇ把计ゑ耕
      f_cmp = Double.parseDouble( vec.get( 0 ).GetToken().PrintToken() ) ;
      
      // **** i眖1秨﹍
      for ( int i = 1 ; i < vec.size() && allTrue ; i++ ) {
        double f_par = Double.parseDouble( vec.get( i ).GetToken().PrintToken() ) ;
        if ( f_cmp < f_par ) f_cmp = f_par ;  // True, р膀非砞把计
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
  // 耞┮Τ把计琌常┪单ウ把计, 琌杠return #t (true), 玥return nil (false)
  static Node IsLessThanOrEqual( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    int i_cmp = 0 ;       // ゑ耕膀非
    double f_cmp = 0 ;     // ゑ耕膀非
    boolean allInteger = true, allTrue = true ;
    
    // 耞把计
    // 璝ぃ琌integerぃ琌float, 碞璶メError Message
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      if ( Evaluate.ParameterType( vec.get( i ) ).equals( "integer" ) ) ;
      else if ( Evaluate.ParameterType( vec.get( i ) ).equals( "float" ) )
        allInteger = false ;
      // 獶计把计
      else throw new MyException( "ERROR (<= with incorrect argument type) : "
                                  + BT.PrintLTree( vec.get( i ), 0, false, "" ), 7 ) ;
    } // for
    
    
    // 狦场把计常琌Integer
    if ( allInteger ) {
      // 材把计秨﹍ゑ耕膀非, 礛籔ぇ把计ゑ耕
      i_cmp = Integer.parseInt( vec.get( 0 ).GetToken().PrintToken() ) ;
      
      // **** i眖1秨﹍
      for ( int i = 1 ; i < vec.size() && allTrue ; i++ ) {
        int i_par = Integer.parseInt( vec.get( i ).GetToken().PrintToken() ) ;
        if ( i_cmp <= i_par ) i_cmp = i_par ;  // True, р膀非砞把计
        else allTrue = false ;
      } // for
    } // if
    
    
    // ㄤいぶΤ把计琌Float
    else {
      // 材把计秨﹍ゑ耕膀非, 礛籔ぇ把计ゑ耕
      f_cmp = Double.parseDouble( vec.get( 0 ).GetToken().PrintToken() ) ;
      
      // **** i眖1秨﹍
      for ( int i = 1 ; i < vec.size() && allTrue ; i++ ) {
        double f_par = Double.parseDouble( vec.get( i ).GetToken().PrintToken() ) ;
        if ( f_cmp <= f_par ) f_cmp = f_par ;  // True, р膀非砞把计
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
  // 耞┮Τ把计琌常单ウ把计, 琌杠return #t (true), 玥return nil (false)
  static Node IsEqualTo( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    int i_cmp = 0 ;       // ゑ耕膀非
    double f_cmp = 0 ;     // ゑ耕膀非
    boolean allInteger = true, allTrue = true ;
    
    // 耞把计
    // 璝ぃ琌integerぃ琌float, 碞璶メError Message
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      if ( Evaluate.ParameterType( vec.get( i ) ).equals( "integer" ) ) ;
      else if ( Evaluate.ParameterType( vec.get( i ) ).equals( "float" ) )
        allInteger = false ;
      // 獶计把计
      else throw new MyException( "ERROR (= with incorrect argument type) : "
                                  + BT.PrintLTree( vec.get( i ), 0, false, "" ), 7 ) ;
    } // for
    
    
    // 狦场把计常琌Integer
    if ( allInteger ) {
      // 材把计秨﹍ゑ耕膀非, 礛籔ぇ把计ゑ耕
      i_cmp = Integer.parseInt( vec.get( 0 ).GetToken().PrintToken() ) ;
      
      // **** i眖1秨﹍
      for ( int i = 1 ; i < vec.size() && allTrue ; i++ ) {
        int i_par = Integer.parseInt( vec.get( i ).GetToken().PrintToken() ) ;
        if ( i_cmp == i_par ) i_cmp = i_par ;  // True, р膀非砞把计
        else allTrue = false ;
      } // for
    } // if
    
    
    // ㄤいぶΤ把计琌Float
    else {
      // 材把计秨﹍ゑ耕膀非, 礛籔ぇ把计ゑ耕
      f_cmp = Double.parseDouble( vec.get( 0 ).GetToken().PrintToken() ) ;
      
      // **** i眖1秨﹍
      for ( int i = 1 ; i < vec.size() && allTrue ; i++ ) {
        double f_par = Double.parseDouble( vec.get( i ).GetToken().PrintToken() ) ;
        if ( f_cmp == f_par ) f_cmp = f_par ;  // True, р膀非砞把计
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
  // 耞把计琌atom node, 琌杠return #t (true), 玥return nil (false)
  static Node IsAtom( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    Token t ;
    Node para = vec.get( 0 ) ;    // 把计 parameter
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
  // 耞把计琌list(S-exp), 琌杠return #t (true), 玥return nil (false)
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
  // 耞把计琌pure list(程娩nil), 琌杠return #t (true), 玥return nil (false)
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
  // 耞把计琌nil, (), #f
  // 琌杠return #t (true), 玥return nil (false)
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
  // 耞把计琌integer, 琌杠return #t (true), 玥return nil (false)
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
  // 耞把计琌real / number, 琌杠return #t (true), 玥return nil (false)
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
  // 耞把计琌string, 琌杠return #t (true), 玥return nil (false)
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
  // 耞把计琌boolean,  #t (true) ┪  #f / nil (false)
  // 琌杠return #t (true), 玥return nil (false)
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
  // 耞把计琌symbol, 琌杠return #t (true), 玥return nil (false)
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
  // р┮Τ﹃把计钡癬ㄓΘ﹃, 礛return
  static Node Str_Append( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    String str = "\"" ;     // 峨秨繷蛮ま腹(")

    // 耞把计
    // 璝ぃ琌string, 碞璶メError Message
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      if ( Evaluate.ParameterType( vec.get( i ) ).equals( "string" ) ) ;
      // 獶﹃把计
      else throw new MyException( "ERROR (string-append with incorrect argument type) : "
                                  + BT.PrintLTree( vec.get( i ), 0, false, "" ), 7 ) ;
    } // for
    
    
    // ㄌ奔–把计繷Ю蛮ま腹("), 礛钡癬ㄓ
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      String temp =  vec.get( i ).GetToken().PrintToken() ;
      str = str + temp.substring( 1, temp.length() - 1 ) ;
    } // for
    
    str = str + "\"" ;      // 钡挡蛮ま腹(")
    
    Token t = new Token( str, 0, 0 ) ;
    t.Classify() ;
    ans.SetToken( t ) ;
    return ans ;
  } // Str_Append()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : string>?
  // 耞┮Τ﹃琌常ウ﹃, 琌杠return #t (true), 玥return nil (false)
  static Node Str_Greater( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    String s_cmp = "" ;       // ゑ耕膀非
    boolean allTrue = true ;
    
    // 耞把计
    // 璝ぃ琌string, 碞璶メError Message
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      if ( Evaluate.ParameterType( vec.get( i ) ).equals( "string" ) ) ;
      // 獶﹃把计
      else throw new MyException( "ERROR (string>? with incorrect argument type) : "
                                  + BT.PrintLTree( vec.get( i ), 0, false, "" ), 7 ) ;
    } // for
    
    
    // 材把计秨﹍ゑ耕膀非, 礛籔ぇ把计ゑ耕
    s_cmp = vec.get( 0 ).GetToken().PrintToken() ;
      
    // **** i眖1秨﹍
    for ( int i = 1 ; i < vec.size() && allTrue ; i++ ) {
      String str = vec.get( i ).GetToken().PrintToken() ;
      // compare挡狦0, ボs_cmp > str
      if ( s_cmp.compareTo( str ) > 0 ) s_cmp = str ;  // True, р膀非砞把计
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
  // 耞┮Τ﹃琌常ウ﹃, 琌杠return #t (true), 玥return nil (false)
  static Node Str_Less( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    String s_cmp = "" ;       // ゑ耕膀非
    boolean allTrue = true ;
    
    // 耞把计
    // 璝ぃ琌string, 碞璶メError Message
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      if ( Evaluate.ParameterType( vec.get( i ) ).equals( "string" ) ) ;
      // 獶﹃把计
      else throw new MyException( "ERROR (string<? with incorrect argument type) : "
                                  + BT.PrintLTree( vec.get( i ), 0, false, "" ), 7 ) ;
    } // for
    
    
    // 材把计秨﹍ゑ耕膀非, 礛籔ぇ把计ゑ耕
    s_cmp = vec.get( 0 ).GetToken().PrintToken() ;
      
    // **** i眖1秨﹍
    for ( int i = 1 ; i < vec.size() && allTrue ; i++ ) {
      String str = vec.get( i ).GetToken().PrintToken() ;
      // compare挡狦0, ボs_cmp < str
      if ( s_cmp.compareTo( str ) < 0 ) s_cmp = str ;  // True, р膀非砞把计
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
  // 耞┮Τ﹃琌常单, 琌杠return #t (true), 玥return nil (false)
  static Node Str_Equal( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    String s_cmp = "" ;       // ゑ耕膀非
    boolean allTrue = true ;
    
    // 耞把计
    // 璝ぃ琌string, 碞璶メError Message
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      if ( Evaluate.ParameterType( vec.get( i ) ).equals( "string" ) ) ;
      // 獶﹃把计
      else throw new MyException( "ERROR (string=? with incorrect argument type) : "
                                  + BT.PrintLTree( vec.get( i ), 0, false, "" ), 7 ) ;
    } // for
    
    
    // 材把计秨﹍ゑ耕膀非, 礛籔ぇ把计ゑ耕
    s_cmp = vec.get( 0 ).GetToken().PrintToken() ;
      
    // **** i眖1秨﹍
    for ( int i = 1 ; i < vec.size() && allTrue ; i++ ) {
      String str = vec.get( i ).GetToken().PrintToken() ;
      if ( s_cmp.equals( str ) ) s_cmp = str ;  // True, р膀非砞把计
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
  // equivalent 单基
  // 耞ㄢ把计琌ぃ琌词攫, 琌杠return #t (true), 玥return nil (false)
  // 璝把计integer┪float(number), 玥琌ぃ琌单
  // 璝琌T┪NIL, 琌return #t (true)
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
  // equal 
  // 耞ㄢ把计┮binding攫琌ぃ琌妓, 琌杠return #t (true), 玥return nil (false)
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
  // 琌 nil杠, return #t (true)
  // 玥return #f / nil (false)
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
  // ㄌevaluate把计
  // 璶Τ把计evaluateЧ挡狦nil, 铬癹伴return nil, ぃ惠恨把计evaluate挡狦
  // 璝true┪evaluate, 玥return程把计evaluateЧ挡狦
  static Node And( Node node ) throws MyException {
    Node ans = new Node(), ans_temp = new Node() ;
    Token t ;
    boolean allTrue = true ;
    
    // -------------------------- 眔临ゼevaluate把计 --------------------------
    Vector<Node> vec = new Vector<Node>() ;
    while ( node != null ) {
      if ( node.GetToken() != null && node.GetToken().GetIntType() == Type.NIL ) ;
      
      // 竊翴琌ATOM NODE
      else if ( node.GetToken() != null ) throw new MyException( "ERROR (non-list) : ", 11 ) ;
      
      else vec.add( node.GetLeft() ) ;
      
      node = node.GetRight() ;
    } // while
    // -------------------------- 眔临ゼevaluate把计 --------------------------
    
    for ( int i = 0 ; i < vec.size() && allTrue ; i++ ) {
      try {
        ans_temp = Evaluate.EvalSExp( vec.get( i ), 1 ) ;
      } catch ( MyException e ) {
        // 兵ンΑevaluateЧ No return value
        if ( e.GetCase() == 88 ) 
          throw new MyException( "ERROR (unbound condition) : " + 
                                 BT.PrintLTree( vec.get( i ), 0, false, "" ), 22 ) ;
        else throw e ;
      } // catch
      
      // 璶Τ琌false, 碞return nil (false)
      if ( ans_temp.GetToken() != null && 
           ans_temp.GetToken().GetIntType() == Type.NIL ) {
        t = new Token( "nil", 0, 0 ) ;
        t.Classify() ;
        ans.SetToken( t ) ;
        allTrue = false ;
      } // if
      
      // else ;
      // 獶ATOM Node ┪ 獶nilATOM Node 碞ぃ暗ㄆ, 膥尿耞把计
    } // for
      
    if ( allTrue ) 
      ans = Evaluate.EvalSExp( vec.get( vec.size() - 1 ), 1 ) ;

    return ans ;
  } // And()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : or
  // ㄌevaluate把计 
  // 璶Τ把计evaluateЧ挡狦true┪磅︽
  // ミㄨreturn 把计evaluateЧ挡狦, ぃ惠恨把计evaluate挡狦
  // 璝nil(false), 玥return nil
  static Node Or( Node node ) throws MyException {
    Node ans = new Node(), ans_temp = new Node() ;
    Token t ;
    
    // -------------------------- 眔临ゼevaluate把计 --------------------------
    Vector<Node> vec = new Vector<Node>() ;
    while ( node != null ) {
      if ( node.GetToken() != null && node.GetToken().GetIntType() == Type.NIL ) ;
      
      // 竊翴琌ATOM NODE
      else if ( node.GetToken() != null ) throw new MyException( "ERROR (non-list) : ", 11 ) ;
      
      else vec.add( node.GetLeft() ) ;
      
      node = node.GetRight() ;
    } // while
    // -------------------------- 眔临ゼevaluate把计 --------------------------
    
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      try {
        ans_temp = Evaluate.EvalSExp( vec.get( i ), 1 ) ;
      } catch ( MyException e ) {
        // 兵ンΑevaluateЧ No return value
        if ( e.GetCase() == 88 ) 
          throw new MyException( "ERROR (unbound condition) : " + 
                                 BT.PrintLTree( vec.get( i ), 0, false, "" ), 22 ) ;
        else throw e ;
      } // catch
      
      // 琌ATOM Node琌false, 碞ぃ暗ㄆ, 膥尿耞把计
      if ( ans_temp.GetToken() != null &&
           ans_temp.GetToken().GetIntType() == Type.NIL ) ;
      
      // 獶ATOM Node ┪ 獶nilATOM Node碞return把计
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
  // 狦把计1(兵ン)evaluateЧ琌 true杠, evaluate把计2return
  // 琌false杠, evaluate把计3return
  static Node If( Node node ) throws MyException {
    Node ans = new Node() ;
    
    // -------------------------- 眔临ゼevaluate把计 --------------------------
    Vector<Node> vec = new Vector<Node>() ;
    while ( node != null ) {
      if ( node.GetToken() != null && node.GetToken().GetIntType() == Type.NIL ) ;
      
      // 竊翴琌ATOM NODE
      else if ( node.GetToken() != null ) throw new MyException( "ERROR (non-list) : ", 11 ) ;
      
      else vec.add( node.GetLeft() ) ;
      
      node = node.GetRight() ;
    } // while
    // -------------------------- 眔临ゼevaluate把计 --------------------------
    
    Node cond = new Node() ;
    // boolean isTrue = false ;
    try {
      cond = Evaluate.EvalSExp( vec.get( 0 ), 1 ) ;  // evaluate琌true or false
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
    
    else    // 琌True ┪琌 磅︽
      ans = Evaluate.EvalSExp( vec.get( 1 ), 1 ) ;
    
    return ans ;
  } // If()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : cond (condition)
  // 贺
  // if (...) ....
  // else if (...) ....
  // else if (...) ....
  // else ....
  // 阀├
  //
  // 把计琌ぃ琌常'獶'ATOM Node
  // 礛–把计柑埃conditon临Τ⊿ΤㄤS-exp, ⊿Τ璶メerror msg
  // 礛ㄌ耞–把计condition s-exp琌ぃ琌True┪琌磅︽
  // 1) 狦兵ンΑevaluateЧ琌 true┪琌磅︽杠
  //    碞磅︽(evaluate)蛤sub-exp, 肚程exp evaluateЧ挡狦
  // 2) 狦兵ンΑevaluateЧ琌false杠, 膥尿evaluate把计, 狡(1)&(2)˙艼
  static Node Cond( Node node ) throws MyException {
    
    Vector<Node> vec = new Vector<Node>() ;  // 材糷┮Τ把计
    Node ans = null ;
    
    // ------------------------------ 眔材糷┮Τ把计 ------------------------------
    while ( node != null ) {  
      // ボ程Ю狠(挡)
      if ( node.GetToken() != null && node.GetToken().GetIntType() == Type.NIL ) ;
      
      // 竊翴琌ATOM NODE
      else if ( node.GetToken() != null ) throw new MyException( "ERROR (non-list) : ", 11 ) ;
      
      else {
        // Type斗list(獶ATOM Node)
        if ( node.GetLeft().GetToken() != null ) throw new MyException( "", 87 ) ;
        else
          vec.add( node.GetLeft() ) ;
      } // else
      
      node = node.GetRight() ;
    } // while
    // ------------------------------ 眔材糷┮Τ把计 ------------------------------
    
    
    // -------------------------- 耞材糷把计琌list -------------------------
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      if ( ! Evaluate.IsPureList( vec.get( i ) ) )
        throw new MyException( "Cond format error_para", 222222 ) ;
    } // for
    // -------------------------- 耞材糷把计琌list -------------------------
    

    // ------------------------------ 眔材糷把计计 ------------------------------

    for ( int i = 0 ; i < vec.size() ; i ++ ) {
      int node_count = 0 ;
      Node temp = vec.get( i ).GetRight() ;
      boolean isEnd = false ;
      for ( Node head = temp ; head != null && ! isEnd ; head = head.GetRight() ) {
        if ( head.GetToken() != null && head.GetToken().GetIntType() == Type.NIL )
          isEnd = true ;
        // 竊翴琌ATOM NODE
        else if ( head.GetToken() != null ) throw new MyException( "lalalalala", 9999 ) ;
        else node_count++ ;
      } // for
    
      // ⊿Τ肚, メerror msg
      if ( node_count == 0 ) throw new MyException( "", 87 ) ;
    } // for
    // ------------------------------ 眔材糷把计计 ------------------------------
    
    
    // ------------------------------ 耞赣秈兵ンΑ ------------------------------
    Node cond = null ;
    
    for ( int i = 0 ; i < vec.size() ; i++ ) {
        
      // else .... 薄猵
      if ( i == vec.size() -1 && vec.get( i ).GetLeft().GetToken() != null
           && vec.get( i ).GetLeft().GetToken().PrintToken().equals( "else" ) ) {

        // -------------------------- 眔evaluate材糷把计 --------------------------
        Vector<Node> sub = new Vector<Node>() ;
        Node temp = vec.get( i ).GetRight() ;
        boolean isEnd = false ;
        for ( Node head = temp ; head != null && ! isEnd ; head = head.GetRight() ) {
          if ( head.GetToken() != null && head.GetToken().GetIntType() == Type.NIL )
            isEnd = true ;
          
          // 竊翴琌ATOM NODE
          else if ( head.GetToken() != null ) throw new MyException( "lalalalala", 9999 ) ;
          
          // Αタ絋 : 璶硂竊翴
          else sub.add( head.GetLeft() ) ;
        } // for
        // -------------------------- 眔evaluate材糷把计 --------------------------
      
        
        // ⊿Τ肚, メerror msg
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
              
          return ans ;  // for癹伴禲Ч, ans琌程s-exp磅︽Ч挡狦
        } // else
      } // if
        
      else cond = Evaluate.EvalSExp( vec.get( i ).GetLeft(), 1 ) ;

        
      // 兵ンΑevaluate挡狦nil碞ぃ暗笆膥尿耞把计(兵ンΑ)
      if ( cond.GetToken() != null && cond.GetToken().GetIntType() == Type.NIL ) ;
        
      // 兵ンΑevaluate挡狦True┪琌磅︽
      else {
        // -------------------------- 眔evaluate材糷把计 --------------------------
        Vector<Node> sub = new Vector<Node>() ;
        Node temp = vec.get( i ).GetRight() ;
        boolean isEnd = false ;
        for ( Node head = temp ; head != null && ! isEnd ; head = head.GetRight() ) {
          if ( head.GetToken() != null && head.GetToken().GetIntType() == Type.NIL )
            isEnd = true ;
          
          // 竊翴琌ATOM NODE
          else if ( head.GetToken() != null ) throw new MyException( "lalalalala", 9999 ) ;
          
          // Αタ絋 : 璶硂竊翴
          else sub.add( head.GetLeft() ) ;
        } // for
        // -------------------------- 眔evaluate材糷把计 --------------------------
      
        
        // ⊿Τ肚, メerror msg
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
              
          return ans ;  // for癹伴禲Ч, ans琌程s-exp磅︽Ч挡狦
        } // else
      } // else
      
    } // for
    
    throw new MyException( "", 88 ) ;
    // ------------------------------ 耞赣秈兵ンΑ ------------------------------
    
  } // Cond()
  // -------------------------------------------------------------------------------
  
  
  // -------------------------------------------------------------------------------
  // Function Name : begin
  // –把计ㄌevaluate
  // return程把计evaluateЧ挡狦(单┮Τㄆ薄常暗Ч)
  static Node Begin( Node node ) throws MyException {
    Vector<Node> vec = new Vector<Node>() ;
    Node ans = new Node() ;
    
    // --------------------- ゼevaluate S-exp ---------------------
    while ( node != null ) {
      if ( node.GetToken() != null && node.GetToken().GetIntType() == Type.NIL ) ;
      
      // 竊翴琌ATOM NODE
      else if ( node.GetToken() != null ) throw new MyException( "ERROR (non-list) : ", 11 ) ;
      
      else vec.add( node.GetLeft() ) ;
      
      node = node.GetRight() ;
    } // while
    // --------------------- ゼevaluate S-exp ---------------------
    
    
    // -------------------- evaluate & 磅︽ S-exp --------------------
    for ( int i = 0 ; i < vec.size() ; i++ ) {
      try {
        ans = Evaluate.EvalSExp( vec.get( i ), 1 ) ;
      } catch ( MyException e ) {
        // evaluateЧ no return value
        if ( e.GetCase() == 88 && i != vec.size() - 1 ) ;
        else throw e ;
      } // catch
    } // for
    // -------------------- evaluate & 磅︽  S-exp --------------------
    
    return ans ;
  } // Begin()
  // -------------------------------------------------------------------------------
  
  
  // -------------------------------------------------------------------------------
  // Function Name : verbose?
  // verbose mode琌秨币
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
  // 肚秈ㄓê把计琌nil
  // 狦琌nil玥闽超verbose mode, 璝獶nil玥秨币verbose mode
  static Node Verbose( Vector<Node> vec ) throws MyException {
    if ( Evaluate.ParameterType( vec.get( 0 ) ).equals( "nil" ) )
      Main.sIsVerbose = false ;
    else Main.sIsVerbose = true ;
    
    return IsVerbose() ;
  } // Verbose()
  // -------------------------------------------------------------------------------
  
  
  // -------------------------------------------------------------------------------
  // Function Name : lambda
  // ﹚竡Ω┦function
  // format: ( lambda ( zero-or-more-symbols ) one-or-more-S-expressions )
  // formatΤerrorメ"LAMBDA format error"error msg
  
  // 耞Lambda format : ( lambda ( zero-or-more-symbols ) one-or-more-S-expressions )
  // 1.耞材把计琌list or () [nil]
  // 2.材把计柑琌常琌symbol
  static Node Lambda( Node node ) throws MyException {
    Token t ;
    Node para1 = node.GetLeft() ;
    Vector<DefObject> f_para = new Vector<DefObject>() ;
    int para_num = 0 ;
    
    // () 礚把计薄猵********************************************
    if ( para1 != null && para1.GetToken() != null &&
         para1.GetToken().GetIntType() == Type.NIL )
      t = new Token( "#<procedure lambda>", 0, 0 ) ;
    // () 礚把计薄猵********************************************
    
    // Τ把计薄猵***********************************************
    // 耞琌ぃ琌柑狥﹁常琌symbol
    else if ( Evaluate.ParameterType( para1 ).equals( "list" ) ) {
      while ( para1 != null ) {
        if ( para1.GetToken() != null && para1.GetToken().GetIntType() == Type.NIL ) ;
        
        // 竊翴琌ATOM NODE
        else if ( para1.GetToken() != null ) throw new MyException( "", 87 ) ;
        
        else if ( para1.GetLeft() != null && para1.GetLeft().GetToken() != null 
                  && para1.GetLeft().GetToken().GetIntType() != Type.SYMBOL )
          throw new MyException( "", 87 ) ;
        
        // 材8肈留旅case  ex: ( lambda ( ( cons 1 2 ) x u ) ( + x u ) )
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
    // Τ把计薄猵***********************************************
    
    else throw new MyException( "", 87 ) ;
    
    
    // ==================== function body ====================
    
    Vector<Node> sxp = new Vector<Node>() ;
    Node para = node.GetRight() ;
    
    while ( para != null ) {
      if ( para.GetToken() != null && para.GetToken().GetIntType() == Type.NIL ) ;
      
      // 竊翴琌ATOM NODE
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
    
    // ぃ斗肚把计function
    if ( def.mFunction_Parameter == null || 
         ( def.mFunction_Parameter != null && def.mFunction_Parameter.isEmpty() ) )
      isHavePara = false ;
    
    // ____________________________ 眔把计(﹚竡跋办跑计) ____________________________
    
    if ( isHavePara ) vec_para = new Vector<DefObject>() ;
    
    int i = 0 ;
    while ( node != null ) {  
      // ボ程Ю狠(挡)
      if ( node.GetToken() != null && node.GetToken().GetIntType() == Type.NIL ) ;
      
      // 竊翴琌ATOM NODE
      else if ( node.GetToken() != null ) throw new MyException( "ERROR (non-list) : ", 11 ) ;
      
      else if ( node.GetLeft() != null ) {
        if ( isHavePara ) {
          
          try {
            
            e_left = Evaluate.EvalSExp( node.GetLeft(), 1 ) ;
            
          } catch ( MyException e ) {
            // 把计evaluateЧ No return value -> error
            if ( e.GetCase() == 88 ) {
              throw new MyException( "ERROR (unbound parameter) : " + 
                                     BT.PrintLTree( node.GetLeft(), 0, false, "" ), 20 ) ;
            } // if
            else throw e ;
          } // catch
          
          DefObject d = Evaluate.FindDefine( e_left, true ) ;
          String p_name = def.GetFunctionParameter().get( i ).GetName() ;
          
          // 狦把计琌 function
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
    // ____________________________ 眔把计(﹚竡跋办跑计) ____________________________
    
    
    // ____________________________ 磅︽function body ____________________________
    Node ans = null ;
    for ( int j = 0 ; j < def.mFunction_Body.size() ; j ++ ) {

      try {
        ans = Evaluate.EvalSExp( def.mFunction_Body.get( j ), 1 ) ;
      } catch ( MyException e ) {
        if ( e.GetCase() == 88 && j != def.mFunction_Body.size() - 1 ) ;
        else throw e ;
      } // catch 
      
    } // for
    // ____________________________ 磅︽function body ____________________________
   

    if ( isHavePara )
      Main.sLocal_Def.remove( Main.sLocal_Def.size() - 1 ) ;   // 矪瞶Ч硂part碞pop奔硂Ω﹚竡把计
    
    return ans ;
  } // User_Function()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : let
  // ﹚竡Ω┦function蛤跋办跑计 (度function bodyΤ)
  // format: ( let '(' { SYMBOL <S-exp> } ')'  one-or-more-S-expressions )
  // formatΤerrorメ"LAMBDA format error"error msg
  
  // 耞Let format : ( let '(' { SYMBOL <S-exp> } ')'  one-or-more-S-expressions )
  // 1.耞材把计琌list or () [nil]
  // 2.材把计柑琌常琌'( SYMBOL <S-exp> )'Α
  
  static Node Let( Node node ) throws MyException {
    Node para1 = node.GetLeft() ;    // 跋办跑计﹚竡跋
    Vector<DefObject> f_para = new Vector<DefObject>() ;   // 纗﹚竡Ч跋办跑计
    int para_num = 0 ;
    boolean havePara = true ;
    
    // () 礚把计薄猵********************************************
    if ( para1 != null && para1.GetToken() != null &&
         para1.GetToken().GetIntType() == Type.NIL ) havePara = false ;
    // () 礚把计薄猵********************************************
    
    // Τ把计薄猵***********************************************
    // 耞琌ぃ琌柑狥﹁常琌symbol
    else if ( Evaluate.ParameterType( para1 ).equals( "list" ) ) {
      while ( para1 != null ) {
        if ( para1.GetToken() != null && para1.GetToken().GetIntType() == Type.NIL ) ;
        
        // 竊翴琌ATOM NODE
        else if ( para1.GetToken() != null ) throw new MyException( "", 87 ) ;
        
        else {
          Node p = para1.GetLeft() ;   // 跋办跑计﹚竡   ex: (x 5) , (y 4)....
          // System.out.println(BT.PrintLTree(p, 0, false, "")) ;
          
          // 斗才 ( SYMBOL <S-exp> ) 硂妓Α      ex: ( x 5 )
          if ( p != null && p.GetToken() == null && p.GetLeft() != null && 
               p.GetRight() != null && p.GetRight().GetToken() == null ) {
            
            // ぃ才list ┪琌 Τ禬ㄢ竊翴(ex: (x 11 12 )) 
            // ┪琌 Τ跑计⊿Τ﹚竡Binding ( p.GetRight().GetLeft() == null )
            if ( ( p.GetRight().GetRight() != null && p.GetRight().GetRight().GetToken() != null
                   && p.GetRight().GetRight().GetToken().GetIntType() != Type.NIL ) ||
                 ( p.GetRight().GetRight() != null && p.GetRight().GetRight().GetToken() == null
                   && p.GetRight().GetRight().GetLeft() != null )
                 || p.GetRight().GetLeft() == null )
              throw new MyException( "", 87 ) ;
            
            Node var_name = p.GetLeft() ;
            Node var_binding = p.GetRight().GetLeft() ;
            
            // 璶砆﹚竡跋办跑计斗SYMBOL
            if ( var_name != null && var_name.GetToken() != null && 
                 var_name.GetToken().GetIntType() == Type.SYMBOL ) {
              
              DefObject isDefined = Evaluate.FindDefine( var_name, true ) ;
              // ┪Binding单常衡    ex : (let ( (x (read)) (car x))  (3 5) )
              // 璶﹚竡symbol╰参ずfunction -> error
              if ( isDefined != null && isDefined.GetType().equals( "function" ) ) 
                throw new MyException( "", 87 ) ;
              
              para_num ++ ;
              DefObject d = new DefObject( var_name.GetToken().PrintToken(), 
                                           "symbol", var_binding ) ;
              f_para.add( d ) ;
              
            } // if
            
            // ﹚竡獶SYMBOL跑计 -> error
            else throw new MyException( "", 87 ) ;
          } // if
          
          // ぃ才 ( SYMBOL <S-exp> ) 硂妓Α -> error
          else throw new MyException( "", 87 ) ;
        } // else
 
        para1 = para1.GetRight() ;
      } // while()
    } // else if
    // Τ把计薄猵***********************************************
    
    else throw new MyException( "", 87 ) ;
    
    
    // 浪琩formatevaluate跋办跑计Binding
    for ( int i = 0 ; i < f_para.size() ; i++ ) {
      try {

        f_para.get( i ).SetBinding( Evaluate.EvalSExp( f_para.get( i ).GetBinding(), 1 ) ) ;

      } catch ( MyException e ) {
        // 璶﹚竡binding evaluateЧ No return value -> error
        if ( e.GetCase() == 88 ) 
          throw new MyException( "ERROR (no return value) : " + 
                                 BT.PrintLTree( f_para.get( i ).GetBinding(), 0, false, "" ), 23 ) ;
        else throw e ;
      } // catch
    } // for

    
    // р﹚竡Ч跋办跑计push秈stack
    if ( havePara ) {
      if ( Main.sLocal_Def == null ) Main.sLocal_Def = new Vector<Vec_DefObject>() ;
      Vec_DefObject v = new Vec_DefObject() ;

      v.SetVec( f_para ) ;
      Main.sLocal_Def.add( v ) ;
    } // if
    
    // ==================== 眔 function body ====================
    
    Vector<Node> f_body = new Vector<Node>() ;
    Node sxp = node.GetRight() ;         // function body
    
    while ( sxp != null ) {
      if ( sxp.GetToken() != null && sxp.GetToken().GetIntType() == Type.NIL ) ;
      
      // 竊翴琌ATOM NODE
      else if ( sxp.GetToken() != null ) throw new MyException( "", 87 ) ;
      
      else f_body.add( sxp.GetLeft() ) ;
      
      sxp = sxp.GetRight() ;
    } // while
    
    // ==================== 眔 function body ====================
    
    
    // ____________________________ 磅︽function body ____________________________
    Node ans = null ;
    for ( int j = 0 ; j < f_body.size() ; j ++ ) {
      try {
        ans = Evaluate.EvalSExp( f_body.get( j ), 1 ) ;
      } catch ( MyException e ) {
        if ( e.GetCase() == 88 && j != f_body.size() - 1 ) ;
        else throw e ;
      } // catch 
    } // for
    // ____________________________ 磅︽function body ____________________________
    
    
    if ( havePara )
      Main.sLocal_Def.remove( Main.sLocal_Def.size() - 1 ) ;   // 矪瞶Ч硂part碞pop奔硂Ω﹚竡跋办跑计
    
    return ans ;
  } // Let()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : read
  // 弄块input (词Ч俱攫)
  static Node Read() throws MyException {
    Node ans = new Node() ;
    
    // ========================== read玡浪琩蛤计﹍て ==========================
    // 词攫, Column_re璶耴箂
    // 狦︽ゼ矪瞶Ч攫┮ノLine_re跑1
    // 狦矪瞶Ч┪琌逞琌White Space┪爹秆玥砞0
    if ( GT.BehindIsWSOrComment( Main.sColumn ) )
      Main.sLine_re = 0 ;
    else Main.sLine_re = 1 ;
    Main.sColumn_re = 0 ;
    // ========================== read玡浪琩蛤计﹍て ==========================
    
    try {
      ans = BT.ReadSExp( Main.s_oReader ) ;
    } catch( MyException e ) {
      if ( e.GetCase() == 1 || e.GetCase() == 2 || e.GetCase() == 4 || e.GetCase() == 5 ) {
        String s = "\"" + e.getMessage().substring( 0, e.getMessage().length()-1 ) + "\"" ;
        Token t = new Token( s, 0, 0 ) ;
        t.Classify() ;
        t.SetType( Type.ERROR ) ;
        ans = new Node( t ) ;
        
        // syntax error : Error祇ネ,俱词攫璶メ奔睲,硂︽inputぃ璶,line_re蛤column璶耴箂
        // 硂柑浪琩蛤睲 : 笿errorメmsg, 旧璓input⊿睲奔穦琵Ω弄s-exp岿
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
  // 块蛤帝把计 (词Ч俱攫)
  static Node Write( Vector<Node> vec ) throws MyException {
    Node para = vec.get( 0 ) ;
    
    if ( ! Evaluate.IsPureList( para ) )
      throw new MyException( "ERROR (non-list) : ", 11 ) ;
    else {
      String str = BT.PrintLTree( para, 0, false, "" ) ;
      System.out.print( str.substring( 0, str.length()-1 ) ) ;  // ぃ传︽才腹
    } // else

    return para ;
  } // Write()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : eval
  // Evaluate蛤帝把计
  // 瘤礛vec肚秈ㄓ玡Τ砆evaluate, 璶Ωevaluateê把计
  static Node Eval( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    // level砞0琌琌穝evaluate, 珿top level
    ans = Evaluate.EvalSExp( vec.get( 0 ), 0 ) ;

    return ans ;
  } // Eval()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : set!
  // 砞﹚把计種竡, parameter1﹚竡parameter2
  // セdefine, ぃ斗﹚璶top level
  static Node Set( Node node ) throws MyException {
    // _______________________________ 跑计跋 _______________________________
    
    Node para1 = node.GetLeft() ;              // 把计1, 砆﹚竡symbol
    Node para2 = node.GetRight().GetLeft() ;   // 把计2, Binding (value)
                                                                                
    DefObject def1 = null, def2 = null, new_def = null ;                        
    Boolean para1_IsDefined = false ;          // 把计1Τ⊿Τ砆﹚竡筁 (sVec_Defぃ把计1)
    Boolean isDefinedinLocal_Let = false ;
    Boolean isDefinedinParameter = false ;
    Boolean isDefinedinGlobal = false ;
    Boolean isFind = false ;                   // тΤ砆﹚竡筁碞癹伴, ぃ礛讽function患穦т玡﹚竡
    int stack = -1 ;          // 癘魁砆﹚竡筁跋办糷stack

    // _______________________________ 跑计跋 _______________________________
    
    
    // _______________________________ 矪瞶把计1 _______________________________
    
    // 把计1琌Symbol
    if ( para1.GetToken() != null && para1.GetToken().GetIntType() == Type.SYMBOL ) {
      // 狦璶﹚竡symbolbinding, define钡ㄢ把计(symbol name & binding)
      // 璝Τㄢ把计メerror msg
      if ( node.GetRight().GetRight() != null && node.GetRight().GetRight().GetToken() == null
           && node.GetRight().GetRight().GetLeft() != null )
        throw new MyException( "", 87 ) ;

      // セ糶猭 : def1 = Evaluate.FindDefine( para1, true ) ;
      
      // ==================== 耞把计1琌砆﹚竡筁 & 砆﹚竡柑 ====================
      
      String name = para1.GetToken().PrintToken() ;
      
      // 狦琌磅︽Let Function  Τ﹚竡跋办跑计
      if ( Main.sIsLetFunction && Main.sLocal_Def != null && ! Main.sLocal_Def.isEmpty() ) {
        for ( int i = Main.sLocal_Def.size() - 1 ; i > - 1 && ! isFind ; i-- ) {
          for ( int j = 0 ; j < Main.sLocal_Def.get( i ).GetVec().size() ; j++ ) {
            // set : 单衡Τт
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
      
      
      // 狦ぃ琌磅︽Let Function  Τ﹚竡把计 
      // lambda 蛤 defineㄓㄏノ﹚竡function, 狦跑计把计い⊿Τт﹚竡, 碞办т
      // 硂娩琌把计т  ( stack程穝push秈ㄓ糷 )
      else if ( ! Main.sIsLetFunction && Main.sLocal_Def != null && ! Main.sLocal_Def.isEmpty() ) {
        for ( int j = 0 ; j < Main.sLocal_Def.lastElement().GetVec().size() ; j++ ) {
          // set : 单衡Τт
          if ( name.equals( Main.sLocal_Def.lastElement().GetVec().get( j ).GetName() ) ) {       
            isDefinedinParameter = true ;
            para1_IsDefined = true ;
            def1 = Main.sLocal_Def.lastElement().GetVec().get( j ) ;
            isFind = true ;
          } // if
        } // for
      } // else if
      
      
      for ( int i = 0 ; i < Main.sVec_Def.size() ; i++ ) {
        // set : 单衡Τт
        if ( name.equals( Main.sVec_Def.get( i ).GetName() ) ) {    
          isDefinedinGlobal = true ;
          para1_IsDefined = true ;
          def1 = Main.sVec_Def.get( i ) ;
          isFind = true ;
        } // if
      } // for
      // ==================== 耞把计1琌砆﹚竡筁 & 砆﹚竡柑 ====================
      
    } // if

    // define format error
    else throw new MyException( "", 87 ) ;
    
   
    // ------------------- 把计1Τ⊿Τ砆﹚竡筁 -------------------
    // 砆﹚竡筁獶ずfunction
    if ( def1 != null && ! def1.GetType().equals( "function" ) ) {
      para1_IsDefined = true ;
      // 癘帝Τ砆﹚竡筁,临ぃremove奔,狦把计2Τノセ﹚竡碞тぃセ﹚竡
      // Main.sVec_Def.remove( def1 ) ;
    } // if

    // 砆﹚竡筁ずfunction
    else if ( def1 != null && def1.GetType().equals( "function" ) )
      // 把计1╰参ずFunction
      throw new MyException( "", 87 ) ;
    
    // def1 == null
    else ;
    // ------------------- 把计1Τ⊿Τ砆﹚竡筁 -------------------
    // _______________________________ 矪瞶把计1 _______________________________
    
    
    // _______________________________ 矪瞶把计2 _______________________________
    
    Node para2_eval = new Node() ;
    try {
      para2_eval = Evaluate.EvalSExp( para2, 1 ) ;
    } catch ( MyException e ) {
      // 璶﹚竡binding evaluateЧ No return value -> error
      if ( e.GetCase() == 88 ) 
        throw new MyException( "ERROR (no return value) : " + 
                               BT.PrintLTree( para2, 0, false, "" ), 23 ) ;
      else throw e ;
    } // catch
    
    if ( para2_eval.GetToken() != null ) {
      def2 = Evaluate.FindDefine( para2_eval, false ) ;
      // 矪瞶 (define x (quote a)) , ┮FindDefine把计肚false
      // 璶binding蛤token妓衡砆﹚竡筁, 蛤token妓 ぃノтウbinding
      //  'a 碞钩 'cons 妓, 碞衡 cons 癬ㄓ琌Τ﹚竡筁, ㄆ龟ぃ琌 binding  #<procedure cons>
    } // if
      

    
    // 把计2琌埃symbolatom node (int,float,string,#t,nil)
    // ぃ惠璶恨def2Τ⊿Τт, ぃ礛 (define a 5 ) (define b a ) 硂case def2獶null
    // 钡binding
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
    
    // 把计2琌竒筁quote矪瞶筁symbol atom node
    // ex : (define a 'hi)
    else if ( para2_eval.GetToken() != null && def2 == null
              && para2_eval.GetToken().GetIntType() == Type.SYMBOL )
      new_def = new DefObject( para1.GetToken().PrintToken(), "symbol", para2_eval ) ;
    
    // 把计2琌獶ATOM Node  ⊿砆﹚竡筁  (  : (1 . 2) )
    // 钡binding
    else if ( para2_eval.GetToken() == null && def2 == null )
      new_def = new DefObject( para1.GetToken().PrintToken(), "symbol", para2_eval ) ;
    
    // 把计2琌砆﹚竡筁function
    // 钡binding (defObjtypefunction_user)
    else if ( def2 != null && ( def2.GetType().equals( "function" ) || 
                                def2.GetType().equals( "function_user" ) ) ) {
      new_def = new DefObject( para1.GetToken().PrintToken(), "function_user", 
                               def2.GetParameterNumber(), def2.GetParameterType() ) ;
      new_def.SetBinding( def2.GetBinding() ) ;
    } // else if
    
    
    // 把计2琌砆﹚竡筁functionㄏノ﹚竡function
    // 钡binding (defObjtypefunction_lambda)
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
    
    // _______________________________ 矪瞶把计2 _______________________________
    
    
    // _______________________________ 矪瞶肚  _______________________________
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
    // _______________________________ 矪瞶肚  _______________________________

  } // Set()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : create-error-object
  // 承Error-Object
  // 把计钡String, ㄤ緇玥メerror msg
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
  // 耞把计琌error-object, 琌玥肚 #t (true), 玥肚 nil (false)
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
  // 耞把计琌string ┪ error-object, 玥メerror msg
  // print ぃ玡蛮ま腹tokenず甧, return把计node
  static Node Display_String( Vector<Node> vec ) throws MyException {
    Node ans = new Node() ;
    Node para = vec.get( 0 ) ;
    if ( ! Evaluate.ParameterType( para ).equals( "string" ) &&
         ! Evaluate.ParameterType( para ).equals( "error" ) )
      throw new MyException( "ERROR (display-string with incorrect argument type) : "
                             + BT.PrintLTree( para, 0, false, "" ), 7 ) ;

    else {
      String str = para.GetToken().PrintToken() ;
      System.out.print( str.substring( 1, str.length()-1 ) ) ;   // 奔玡蛮ま腹
      ans = para ;
    } // else
    
    return ans ;
  } // Display_String()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : newline
  // print '\n' , return nil
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
  // 钡symbol把计, ノstringよΑreturn把计
  static Node SymbolToString( Vector<Node> vec ) throws MyException {
    Node para = vec.get( 0 ) ;
    if ( para != null && ! Evaluate.ParameterType( para ).equals( "symbol" ) )
      throw new MyException( "ERROR (symbol->string with incorrect argument type) : "
                             + BT.PrintLTree( para, 0, false, "" ), 7 ) ;
    Token t ;
    String str = "\"" + para.GetToken().PrintToken() + "\"" ;    // 玡蛮ま腹跑Θstring
    t = new Token( str, 0, 0 ) ;
    t.Classify() ;
    return new Node( t ) ;
  } // SymbolToString()
  // -------------------------------------------------------------------------------
  
  
  
  // -------------------------------------------------------------------------------
  // Function Name : number->string
  // 钡symbol把计, ノstringよΑreturn把计
  static Node NumberToString( Vector<Node> vec ) throws MyException {
    Node para = vec.get( 0 ) ;
    if ( ! Evaluate.ParameterType( para ).equals( "integer" ) &&
         ! Evaluate.ParameterType( para ).equals( "float" ) )
      throw new MyException( "ERROR (number->string with incorrect argument type) : "
                             + BT.PrintLTree( para, 0, false, "" ), 7 ) ;
    Token t ;
    String str = "\"" + para.GetToken().PrintToken() + "\"" ;    // 玡蛮ま腹跑Θstring
    t = new Token( str, 0, 0 ) ;
    t.Classify() ;
    return new Node( t ) ;
  } // NumberToString()
  // -------------------------------------------------------------------------------
  
  
} // class MyFunctions
