package PL105_10227241;
import java.util.Scanner ;
import java.util.Vector;


class Main {
  static int sTestNum = -1 ;
  // 讀取測試數據的序號  (PAL用)
  static Scanner s_oReader = new Scanner( System.in ) ;   
  // 讀取input用 (因為Read()也會用到故宣告為全域變數)

  static int sLine = 0 ;             // 真實input的line number
  static int sColumn = 0;            // 真實input的column number
  static String sStr = null ;        // 存取讀入一整行的內容

  static int sLine_re = 0 ;      // 建樹期間跑的line number,建好樹或是出現error時即歸零
  static int sColumn_re = 0;     // 建樹期間跑的column number,建好樹或是出現error時即歸零

  static Vector<DefObject> sVec_Def ;                 // 存取被定義的Symbol跟Function
  static Vector<Vec_DefObject> sLocal_Def = null ;    // 存取區域變數
  
  // -------- proj3 --------
  static boolean sIsVerbose = true ;         // verbose mode
  static DefObject sLambda_Temp = null ;     // 暫存lambda定義的function
  static boolean sIsLetFunction = false ;    // 所要執行的function是否為let
  // -------- proj3 --------
  
  
  public static void main( String[] args ) throws Throwable {
    // TODO Auto-generated method stub
    
    sTestNum = s_oReader.nextInt() ;
    System.out.println( "Welcome to OurScheme!\n" ) ;
    s_oReader.nextLine() ;  // 讀掉"\n" ;
    
    sVec_Def = Evaluate.InitDefObject() ;
    Node head = null ;
    
    try {
      while ( true ) {
        System.out.print( "> " ) ;
        try {
          // =======================================
          // 在前面清空跟判斷剩下是否為White Space或註解的原因:
          // 避免遇到 evaluate error, 然後原本存取的字串未清空, 但後面又只剩下White Space或註解
          // 故在迴圈的一開始判斷這件事及清空
          head = null ;
          
          // 建好一棵樹後, Column_re要歸零
          // 如果同一行未處理完建樹所用的Line_re變為1
          // 如果處理完或是剩下是White Space或註解則設為0
          if ( GT.BehindIsWSOrComment( sColumn ) )
            sLine_re = 0 ;
          else sLine_re = 1 ;
          sColumn_re = 0 ;
          // =======================================
          
          head = BT.ReadSExp( s_oReader ) ;
          if ( head != null ) {
            
            if ( ! Evaluate.IsPureList( head ) )
              throw new MyException( "ERROR (non-list) : ", 11 ) ;
            
            Node ans = Evaluate.EvalSExp( head, 0 ) ;
            
            if ( ans != null ) {
              String str = "" ;
              System.out.println( BT.PrintLTree( ans, 0, false, str ) ) ;
              sLambda_Temp = null ;     // 每次evaluate完要把暫存的lambda function清空
              sLocal_Def = null ;       // 每次evaluate完要把區域變數清空
              sIsLetFunction = false ;  // 每次evaluate完要告訴系統重新開始, 不在let function裡面
            } // if
            
            else if ( ans == null ) System.out.println() ;
          } // if
          
        } catch ( MyException e ) {
           
          // 如果exception不是EOF也不是Exit就輸出訊息並繼續迴圈
          // 如果是EOF或是Exit則丟Exception出迴圈
          if ( e.GetCase() == 11 )
            // non-list後面要印input的pretty print, 故在這邊印
            System.out.println( e.getMessage() + BT.PrintLTree( head, 0, false, "" ) ) ;
          
          else if ( e.GetCase() == 88 )       //  if / cond : No return value (Top-level)
            System.out.println( "ERROR (no return value) : " + BT.PrintLTree( head, 0, false, "" ) ) ;
          
          else if ( e.GetCase() != 1 && e.GetCase() != 3 ) 
            System.out.println( e.getMessage() ) ;
          else throw e ;
          
          // syntax error : Error發生的時候,整棵樹要丟掉清空,這行input也不要,line_re跟column也要歸零
          // evaluate error : 丟掉樹就好, 原本讀的字串跟column不要清空
          head = null ;
          sLine_re = 0 ;
          sColumn_re = 0 ;
          sLambda_Temp = null ;   // 每次evaluate完要把暫存的lambda function清空
          sLocal_Def = null ;     // 每次evaluate完要把區域變數清空
          sIsLetFunction = false ; // 每次evaluate完要告訴系統重新開始, 不在let function裡面
          if ( e.GetCase() <= 5 ) {
            sStr = null ;
            sColumn = 0 ;
          } // if  ( syntax error )
          
        } // catch
      } // while
    } catch ( MyException e ) {
      // catch EOF or Exit
      System.out.println( e.getMessage() ) ;
    } // catch
    
    System.out.println( "Thanks for using OurScheme!" ) ;
    
  } // main()
  

  // ========================= 用來測試的Function =========================
  static void Print_Def() {
    System.out.println( "\n/////////////// Print Definition ///////////////\n" ) ;
    if ( sLocal_Def != null ) {
      System.out.println( "____________ Stack ____________\n" ) ;
      for( int i = Main.sLocal_Def.size() - 1 ; i > - 1 ; i-- ) {
        for ( int j = 0 ; j < Main.sLocal_Def.get( i ).GetVec().size() ; j++ ) {
          System.out.println( "Stack : " + i + ", Index : " + j ) ;
          System.out.print( "Name : " + Main.sLocal_Def.get( i ).GetVec().get( j ).GetName() ) ;
          if ( Main.sLocal_Def.get( i ).GetVec().get( j ).GetType().equals("symbol") ) {
            System.out.print( ", Value : " + BT.PrintLTree( sLocal_Def.get( i ).GetVec().get( j ).GetBinding(),
                                                            0, false, "" ) ) ;
          } // if
          else {
            System.out.print( "\n Function Para : " ) ;
             for ( int k = 0 ; k < sLocal_Def.get( i ).GetVec().get( j ).GetParameterNumber() ; k++ ) {
              System.out.print( sLocal_Def.get( i ).GetVec().get( j ).GetFunctionParameter().get( k ).GetName() + " " ) ;
            } // for
            System.out.print( "\n Function Body : " ) ;
            for ( int k = 0 ; k < sLocal_Def.get( i ).GetVec().get( j ).GetFunctionBody().size() ; k++ ) {
              System.out.println( BT.PrintLTree(sLocal_Def.get( i ).GetVec().get( j ).GetFunctionBody().get( k ), 0, false, "" ) ) ;
            } // for
          } // else
        } // for
      } // for
      System.out.println( "____________ Stack ____________\n" ) ;
    } // if
    
    System.out.println( "____________ Global ____________\n" ) ;
    for ( int j = 0 ; j < Main.sVec_Def.size() ; j++ ) {
      System.out.println( "Global Index : " + j ) ;
      System.out.print( "Name : " + Main.sVec_Def.get( j ).GetName() ) ;
      if (  Main.sVec_Def.get( j ).GetType().equals("symbol") ) {
        System.out.print( ", Value : " + BT.PrintLTree(  Main.sVec_Def.get( j ).GetBinding(),
                                                         0, false, "" ) ) ;
      } // if
      else {
        System.out.print( "\n Function Para : " ) ;
        for ( int k = 0 ; k <  Main.sVec_Def.get( j ).GetParameterNumber() ; k++ ) {
          if ( Main.sVec_Def.get( j ).GetFunctionParameter() != null )
            System.out.print(  Main.sVec_Def.get( j ).GetFunctionParameter().get( k ).GetName() + " " ) ;
        } // for
        System.out.print( "\n Function Body : " ) ;
        for ( int k = 0 ; Main.sVec_Def.get( j ).GetFunctionBody() != null && k <  Main.sVec_Def.get( j ).GetFunctionBody().size() ; k++ ) {
          if ( Main.sVec_Def.get( j ).GetFunctionBody() != null )
            System.out.println( BT.PrintLTree( Main.sVec_Def.get( j ).GetFunctionBody().get( k ), 0, false, "" ) ) ;
        } // for
      } // else
    } // for
    System.out.println( "____________ Global ____________\n" ) ;
    System.out.println( "\n/////////////// Print Definition ///////////////\n" ) ;
  } // Print_Def()
  // ========================= 用來測試的Function =========================
  
} // class Main