package PL105_10227241;
import java.util.Scanner ;
import java.util.Vector;


class Main {
  static int sTestNum = -1 ;
  // Ū�����ռƾڪ��Ǹ�  (PAL��)
  static Scanner s_oReader = new Scanner( System.in ) ;   
  // Ū��input�� (�]��Read()�]�|�Ψ�G�ŧi�������ܼ�)

  static int sLine = 0 ;             // �u��input��line number
  static int sColumn = 0;            // �u��input��column number
  static String sStr = null ;        // �s��Ū�J�@��檺���e

  static int sLine_re = 0 ;      // �ؾ�����]��line number,�ئn��άO�X�{error�ɧY�k�s
  static int sColumn_re = 0;     // �ؾ�����]��column number,�ئn��άO�X�{error�ɧY�k�s

  static Vector<DefObject> sVec_Def ;                 // �s���Q�w�q��Symbol��Function
  static Vector<Vec_DefObject> sLocal_Def = null ;    // �s���ϰ��ܼ�
  
  // -------- proj3 --------
  static boolean sIsVerbose = true ;         // verbose mode
  static DefObject sLambda_Temp = null ;     // �Ȧslambda�w�q��function
  static boolean sIsLetFunction = false ;    // �ҭn���檺function�O�_��let
  // -------- proj3 --------
  
  
  public static void main( String[] args ) throws Throwable {
    // TODO Auto-generated method stub
    
    sTestNum = s_oReader.nextInt() ;
    System.out.println( "Welcome to OurScheme!\n" ) ;
    s_oReader.nextLine() ;  // Ū��"\n" ;
    
    sVec_Def = Evaluate.InitDefObject() ;
    Node head = null ;
    
    try {
      while ( true ) {
        System.out.print( "> " ) ;
        try {
          // =======================================
          // �b�e���M�Ÿ�P�_�ѤU�O�_��White Space�ε��Ѫ���]:
          // �קK�J�� evaluate error, �M��쥻�s�����r�ꥼ�M��, ���᭱�S�u�ѤUWhite Space�ε���
          // �G�b�j�骺�@�}�l�P�_�o��ƤβM��
          head = null ;
          
          // �ئn�@�ʾ��, Column_re�n�k�s
          // �p�G�P�@�楼�B�z���ؾ�ҥΪ�Line_re�ܬ�1
          // �p�G�B�z���άO�ѤU�OWhite Space�ε��ѫh�]��0
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
              sLambda_Temp = null ;     // �C��evaluate���n��Ȧs��lambda function�M��
              sLocal_Def = null ;       // �C��evaluate���n��ϰ��ܼƲM��
              sIsLetFunction = false ;  // �C��evaluate���n�i�D�t�έ��s�}�l, ���blet function�̭�
            } // if
            
            else if ( ans == null ) System.out.println() ;
          } // if
          
        } catch ( MyException e ) {
           
          // �p�Gexception���OEOF�]���OExit�N��X�T�����~��j��
          // �p�G�OEOF�άOExit�h��Exception�X�j��
          if ( e.GetCase() == 11 )
            // non-list�᭱�n�Linput��pretty print, �G�b�o��L
            System.out.println( e.getMessage() + BT.PrintLTree( head, 0, false, "" ) ) ;
          
          else if ( e.GetCase() == 88 )       //  if / cond : No return value (Top-level)
            System.out.println( "ERROR (no return value) : " + BT.PrintLTree( head, 0, false, "" ) ) ;
          
          else if ( e.GetCase() != 1 && e.GetCase() != 3 ) 
            System.out.println( e.getMessage() ) ;
          else throw e ;
          
          // syntax error : Error�o�ͪ��ɭ�,��ʾ�n�ᱼ�M��,�o��input�]���n,line_re��column�]�n�k�s
          // evaluate error : �ᱼ��N�n, �쥻Ū���r���column���n�M��
          head = null ;
          sLine_re = 0 ;
          sColumn_re = 0 ;
          sLambda_Temp = null ;   // �C��evaluate���n��Ȧs��lambda function�M��
          sLocal_Def = null ;     // �C��evaluate���n��ϰ��ܼƲM��
          sIsLetFunction = false ; // �C��evaluate���n�i�D�t�έ��s�}�l, ���blet function�̭�
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
  

  // ========================= �ΨӴ��ժ�Function =========================
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
  // ========================= �ΨӴ��ժ�Function =========================
  
} // class Main