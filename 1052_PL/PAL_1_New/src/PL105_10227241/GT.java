package PL105_10227241 ;

import java.util.Scanner ;


// Functions of Get Token

class GT {

  
  // ===============================  Get Token  ===============================
  
  // �NŪ�J��Input�̾�separator��project�����O�w�q��Token
  // return �@�� Token
  static Token GetToken( Scanner oReader ) throws MyException {
    char ch = '0' ;
    String temp = "" ;      // �s��Token�����e
    Token token ;
    int t_col = -1, t_line = -1 ;    // t_col��t_line����token���_�l��m
  

    ReadBuffer( oReader ) ;

    // ------------------������ Get Token �j�� ������------------------ 
    // �`�N sColumn�[����D
    while ( true ) {
      SkipWS( oReader ) ;
      ch = GetChar( Main.sStr, Main.sColumn ) ;
        
      // if this char is separator.
      if ( IsSeparator( ch ) ) {
        t_col = Main.sColumn_re ;
        t_line = Main.sLine_re ;
        temp = temp + ch ;
        
        // ------------------ �p�G���r����';' ------------------
        if ( ch == ';' ) {
          Main.sColumn = Main.sStr.length() ;
          temp = "" ;
        } // if
        // ------------------ �p�G���r����';' ------------------
        
        
        // ------------------ �p�G���r����'(' ------------------
        else if ( ch == '(' ) {
          Main.sColumn ++ ;
          Main.sColumn_re++ ;
          SkipWS( oReader ) ;
          ch = GetChar( Main.sStr, Main.sColumn ) ;
          if ( ch == ')' ) {     // if next non white space char is ')'
            temp = temp + ch ;
            Main.sColumn ++ ;
            Main.sColumn_re++ ;
            token = new Token( temp, t_line, t_col ) ;
            return token ;
          } // if
          else {
            token = new Token( temp, Main.sLine_re, t_col ) ;
            // Main.sColumn-- ;  // �]���᭱����return �G���|�����j��̫᪺col++ �ҥH���δ�
            return token ;
          } // else 
        } // else if
        // ------------------ �p�G���r����'(' ------------------
        
        
        // ------------------ �p�G���r���� ')' �� '\'' ------------------
        else if ( ch == ')' || ch == '\'' ) {
          token = new Token( temp, Main.sLine_re, t_col ) ;
          Main.sColumn ++ ;
          Main.sColumn_re++ ;
          return token ;
        } // else if
        // ------------------ �p�G���r���� ')' �� '\'' ------------------
          
        
        // ------------------ �p�G���r����'"' ------------------
        else if ( ch == '"' ) {
          Main.sColumn++ ;
          Main.sColumn_re++ ;
          temp = IsString( temp ) ;
          // if ( temp != null )
          token = new Token( temp, Main.sLine_re, t_col ) ;
          // else return null ;
          Main.sColumn ++ ;
          Main.sColumn_re++ ;
          return token ;
        } // else if
        // ------------------ �p�G���r����'"' ------------------
        
      } // if this char is separator
      
      
      // Next character is separator.
      else if ( Main.sColumn + 1 < Main.sStr.length() &&
                IsSeparator( GetChar( Main.sStr, Main.sColumn + 1 ) ) ) {
        temp = temp + ch ;
        token = new Token( temp, Main.sLine_re, Main.sColumn_re - temp.length() + 1 ) ;
        Main.sColumn ++ ;
        Main.sColumn_re++ ;
        return token ;
      } // else if
    
    
      // Neither this char nor the next char is not separator
      // And this char is the last char of the line
      else if ( Main.sColumn + 1 == Main.sStr.length() ) {
        temp = temp + ch ;
        token = new Token( temp, Main.sLine_re, Main.sColumn_re - temp.length() + 1 ) ;
        Main.sColumn ++ ;
        Main.sColumn_re++ ;
        return token ;
      } // else if
    
      else temp = temp + ch ;
     
      Main.sColumn++ ;
      Main.sColumn_re++ ;
      ReadBuffer( oReader ) ;
    } // while
    // ------------------������ Get Token �j�� ������------------------
  } // GetToken()
  
  // ===============================  Get Token  ===============================
  
  
  
  
  static char GetChar( String line, int i ) {
    return line.charAt( i ) ;
  } // GetChar()
  
  
  
  // �T�{�ثe�B�z�����檺String�O�_�B�z���F
  // �p�G�B�z���F,�N�AŪ�U�@��
  // �p�G�٨S�B�z���N������
  static void ReadBuffer( Scanner oReader ) throws MyException {
    boolean isOK = false ;
    
    while ( ! isOK ) {
      // �p�G�ثe�o��w�g����get��Token�B����input��Ū�J�B�z�A�YŪ�J�U�@��
      if ( oReader.hasNext() && ( Main.sStr == null || Main.sColumn >= Main.sStr.length() ) ) {
        Main.sStr = oReader.nextLine() ;
        Main.sLine++ ;
        Main.sLine_re++ ;
        Main.sColumn = 0 ;
        Main.sColumn_re = 0 ;
      } // if
    
      // �p�G�ثe�o��w�g����get��Token�B�S����Ū�J�B�z��input�A�Y�^��NULL
      else if ( ! oReader.hasNext() && ( Main.sStr == null || Main.sColumn >= Main.sStr.length() ) )
        throw new MyException( "ERROR (no more input) : END-OF-FILE encountered", 1 ) ;
      
      else isOK = true ;
    } // while
  } // ReadBuffer()
  
  
  
  // ���L White Space
  // �J��DWhite Space���r���Y���X�j��æ^��
  // ����Main.sColumn���ӫDWhite Space�r����index
  static void SkipWS( Scanner oReader ) throws MyException {
    boolean isNotWS = false ;
    
    // i++ ;     // �ݤU�@�ӬO�_���ť� (�ª�GetToken�g�k)
    ReadBuffer( oReader ) ;
    while ( ! isNotWS ) {
      char ch = GetChar( Main.sStr, Main.sColumn ) ;
      if ( ch != ' ' && ch != '\n' && ch != '\t' ) {
        isNotWS = true ;
      } // if
      else {
        Main.sColumn++ ;
        Main.sColumn_re++ ;
      } // else
      
      ReadBuffer( oReader ) ;
    } // while
    
    return ;
  } // SkipWS()
  
  
  
  // �P�_���r���O�_��Separator
  static boolean IsSeparator( char ch ) {
    if ( ch == '(' || ch == ')' || ch == '\'' || ch == '"' || ch == ';' || 
         ch == ' ' || ch == '\n' || ch == '\t' )
      return true ;
    else return false ;
  } // IsSeparator()
  
  
  // �P�_���r���O�_�� White Space
  static boolean IsWS( char ch ) {
    if ( ch == ' ' || ch == '\n' || ch == '\t' ) return true ;
    else return false ;
  } // IsWS()
  
  
  
  // �P�_�O�_���r��
  // �^�Ƿ�@�r�굲����'"'����m
  // �p�G�^�Ǫ���m����r�����, �N��Ū�����o�S�����'"'  ->  Error
  static String IsString( String temp ) throws MyException {
    
    while ( Main.sColumn < Main.sStr.length() ) {
      char ch = GetChar( Main.sStr, Main.sColumn ) ;
      
      if ( ch == '"' ) {
        temp = temp + ch ;
        return temp ;
      } // if

      
      // �p�G�o�r���O'\' �˱׽u
      else if ( ch == '\\' ) {
        Main.sColumn ++ ;
        Main.sColumn_re ++ ;
        char nextChar = GetChar( Main.sStr, Main.sColumn ) ;
        
        if ( nextChar == 'n' ) temp = temp + '\n' ;
        else if ( nextChar == 't' ) temp = temp + '\t' ;
        else if ( nextChar == '\'' ) temp = temp + '\'' ;
        else if ( nextChar == '"' ) temp = temp + '"' ;
        else if ( nextChar == '\\' ) temp = temp + '\\' ;
        else {
          temp = temp + ch ;
          Main.sColumn -- ;
          Main.sColumn_re -- ;
        } // else
      } // else if
      
      else temp = temp + ch ;
      
      Main.sColumn ++ ;
      Main.sColumn_re ++ ;
    } // while
    
    
    // �p�G����F���٨S���@��������'"',�h��XError�T��
    // ��sLine_re��sColumn_re��X
    throw new MyException( "ERROR (no closing quote) : END-OF-LINE encountered at Line "
                           + Main.sLine_re + " Column " + ( Main.sColumn_re + 1 ) + "\n", 2 ) ;
    
  } // IsString()
  
  
  
  // �d�ݨ��@��ثe�B�z������m�᭱�O���O���ѤUWhite Space �άO ����
  // �p�G�O�h�^��true,��ܤU�� Get Token �o�椣�p�J���
  // �p�G�᭱�O�����N�q���r��,�h�^��false,��ܤU�� Get Token �o�檺��Ƴ]��1
  static boolean BehindIsWSOrComment( int num ) {
    if ( Main.sStr != null ) {
      for ( int i = num ; i < Main.sStr.length() ; i++ ) {
        char ch = Main.sStr.charAt( i ) ;
        if ( ch != ' ' && ch != '\n' && ch != '\t' && ch != ';' ) return false ;
        else if ( ch == ';' ) return true ;
      } // for
      
      return true ;
    } // if
    
    else return true ;
    
  } // BehindIsWSOrComment()

} // class GT