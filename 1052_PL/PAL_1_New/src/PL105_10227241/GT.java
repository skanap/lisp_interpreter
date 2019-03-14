package PL105_10227241 ;

import java.util.Scanner ;


// Functions of Get Token

class GT {

  
  // ===============================  Get Token  ===============================
  
  // 將讀入的Input依據separator跟project的型別定義切Token
  // return 一個 Token
  static Token GetToken( Scanner oReader ) throws MyException {
    char ch = '0' ;
    String temp = "" ;      // 存取Token的內容
    Token token ;
    int t_col = -1, t_line = -1 ;    // t_col跟t_line紀錄token的起始位置
  

    ReadBuffer( oReader ) ;

    // ------------------↓↓↓ Get Token 迴圈 ↓↓↓------------------ 
    // 注意 sColumn加減問題
    while ( true ) {
      SkipWS( oReader ) ;
      ch = GetChar( Main.sStr, Main.sColumn ) ;
        
      // if this char is separator.
      if ( IsSeparator( ch ) ) {
        t_col = Main.sColumn_re ;
        t_line = Main.sLine_re ;
        temp = temp + ch ;
        
        // ------------------ 如果此字元為';' ------------------
        if ( ch == ';' ) {
          Main.sColumn = Main.sStr.length() ;
          temp = "" ;
        } // if
        // ------------------ 如果此字元為';' ------------------
        
        
        // ------------------ 如果此字元為'(' ------------------
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
            // Main.sColumn-- ;  // 因為後面直接return 故不會執行到迴圈最後的col++ 所以不用減
            return token ;
          } // else 
        } // else if
        // ------------------ 如果此字元為'(' ------------------
        
        
        // ------------------ 如果此字元為 ')' 或 '\'' ------------------
        else if ( ch == ')' || ch == '\'' ) {
          token = new Token( temp, Main.sLine_re, t_col ) ;
          Main.sColumn ++ ;
          Main.sColumn_re++ ;
          return token ;
        } // else if
        // ------------------ 如果此字元為 ')' 或 '\'' ------------------
          
        
        // ------------------ 如果此字元為'"' ------------------
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
        // ------------------ 如果此字元為'"' ------------------
        
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
    // ------------------↑↑↑ Get Token 迴圈 ↑↑↑------------------
  } // GetToken()
  
  // ===============================  Get Token  ===============================
  
  
  
  
  static char GetChar( String line, int i ) {
    return line.charAt( i ) ;
  } // GetChar()
  
  
  
  // 確認目前處理的那行的String是否處理完了
  // 如果處理完了,就再讀下一行
  // 如果還沒處理完就不做事
  static void ReadBuffer( Scanner oReader ) throws MyException {
    boolean isOK = false ;
    
    while ( ! isOK ) {
      // 如果目前這行已經全部get完Token且仍有input未讀入處理，即讀入下一行
      if ( oReader.hasNext() && ( Main.sStr == null || Main.sColumn >= Main.sStr.length() ) ) {
        Main.sStr = oReader.nextLine() ;
        Main.sLine++ ;
        Main.sLine_re++ ;
        Main.sColumn = 0 ;
        Main.sColumn_re = 0 ;
      } // if
    
      // 如果目前這行已經全部get完Token且沒有未讀入處理的input，即回傳NULL
      else if ( ! oReader.hasNext() && ( Main.sStr == null || Main.sColumn >= Main.sStr.length() ) )
        throw new MyException( "ERROR (no more input) : END-OF-FILE encountered", 1 ) ;
      
      else isOK = true ;
    } // while
  } // ReadBuffer()
  
  
  
  // 跳過 White Space
  // 遇到非White Space的字元即跳出迴圈並回傳
  // 此時Main.sColumn表那個非White Space字元的index
  static void SkipWS( Scanner oReader ) throws MyException {
    boolean isNotWS = false ;
    
    // i++ ;     // 看下一個是否為空白 (舊的GetToken寫法)
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
  
  
  
  // 判斷此字元是否為Separator
  static boolean IsSeparator( char ch ) {
    if ( ch == '(' || ch == ')' || ch == '\'' || ch == '"' || ch == ';' || 
         ch == ' ' || ch == '\n' || ch == '\t' )
      return true ;
    else return false ;
  } // IsSeparator()
  
  
  // 判斷此字元是否為 White Space
  static boolean IsWS( char ch ) {
    if ( ch == ' ' || ch == '\n' || ch == '\t' ) return true ;
    else return false ;
  } // IsWS()
  
  
  
  // 判斷是否為字串
  // 回傳當作字串結束的'"'的位置
  // 如果回傳的位置等於字串長度, 代表讀完整行卻沒有找到'"'  ->  Error
  static String IsString( String temp ) throws MyException {
    
    while ( Main.sColumn < Main.sStr.length() ) {
      char ch = GetChar( Main.sStr, Main.sColumn ) ;
      
      if ( ch == '"' ) {
        temp = temp + ch ;
        return temp ;
      } // if

      
      // 如果這字元是'\' 倒斜線
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
    
    
    // 如果換行了但還沒找到作為結束的'"',則丟出Error訊息
    // 用sLine_re跟sColumn_re輸出
    throw new MyException( "ERROR (no closing quote) : END-OF-LINE encountered at Line "
                           + Main.sLine_re + " Column " + ( Main.sColumn_re + 1 ) + "\n", 2 ) ;
    
  } // IsString()
  
  
  
  // 查看那一行目前處理完的位置後面是不是都剩下White Space 或是 註解
  // 如果是則回傳true,表示下次 Get Token 這行不計入行數
  // 如果後面是有有意義的字元,則回傳false,表示下次 Get Token 這行的行數設為1
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