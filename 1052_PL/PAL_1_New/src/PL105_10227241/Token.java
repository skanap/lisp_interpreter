package PL105_10227241;

class Token {
  private String mToken ;
  private int mType ;
  private int mLine ;
  private int mColumn ;
  
  Token() {
    mToken = "" ;
  } // Token()
  
  Token( String sToken, int iLine, int iColumn ) {
    mToken = sToken ;
    mLine = iLine ;
    mColumn = iColumn + 1 ;  // �ѼƶǶi�Ӫ��Oindex, �ҥH�n+1
  } // Token()
  
  
  String PrintToken() {
    if ( mType == Type.NIL ) return "nil" ;
    else if ( mType == Type.T ) return "#t" ;
    else if ( mType == Type.QUOTE ) return "quote" ;
    else return mToken ;
  } // PrintToken()
  
  
  String GetStringType() {
    if ( mType == Type.LP ) return "LP" ;
    else if ( mType == Type.RP ) return "RP" ;
    else if ( mType == Type.INT ) return "INT" ;
    else if ( mType == Type.FLOAT ) return "FLOAT" ;
    else if ( mType == Type.STRING ) return "STRING" ;
    else if ( mType == Type.NIL ) return "NIL" ;
    else if ( mType == Type.T ) return "T" ;
    else if ( mType == Type.DOT ) return "DOT" ;
    else if ( mType == Type.QUOTE ) return "QUOTE" ;
    else if ( mType == Type.SYMBOL ) return "SYMBOL" ;
    else return null ;
  } // GetStringType()
  
  int GetIntType() {
    return mType ;
  } // GetIntType()
  
  int GetLine() {
    return mLine ;
  } // GetLine()
  
  int GetColumn() {
    return mColumn ;
  } // GetColumn()
  
  int GetLength() {
    return mToken.length() ;
  } // GetLength()
  
  
  // ***** �ȭ���אּError-Object�ϥ�
  void SetType( int type ) {
    mType = type ;
  } // SetType()
  // ***** �ȭ���אּError-Object�ϥ�
  
  
  // �̾�token�����e����,�ó]�wType
  void Classify() {
    if ( mToken.equals( "(" ) ) this.mType = Type.LP ;
    else if ( mToken.equals( ")" ) ) this.mType = Type.RP ;
    else if ( mToken.equals( "." ) ) this.mType = Type.DOT ;
    else if ( mToken.equals( "'" ) ) this.mType = Type.QUOTE ;
    else if ( mToken.equals( "t" ) || mToken.equals( "#t" ) ) this.mType = Type.T ;
    else if ( mToken.equals( "nil" ) || mToken.equals( "#f" ) || mToken.equals( "()" ) )
      this.mType = Type.NIL ;
    
    else if ( mToken.equals( "+." ) || mToken.equals( "-." ) || mToken.equals( ".+" )
              || mToken.equals( ".-" ) || mToken.equals( "-" ) || mToken.equals( "+" ) )
      this.mType = Type.SYMBOL ;
    
    else if ( mToken.charAt( 0 ) == '"' &&
              mToken.charAt( mToken.length()-1 ) == '"' ) this.mType = Type.STRING ;
    
    else if ( IsInt( mToken ) ) {
      // �p�G�Ʀr�e����+,��+����ʲ���  (jre1.6 �p�G���]�t+��,�নinteger�|�X��)
      if ( mToken.charAt( 0 ) == '+' ) mToken = mToken.substring( 1 ) ;
      this.mType = Type.INT ;
      int temp = Integer.parseInt( mToken ) ;
      this.mToken = Integer.toString( temp ) ;
    } // else if
    
    else if ( IsFloat( mToken ) ) {
      this.mType = Type.FLOAT ;
      double temp = Double.parseDouble( mToken ) ;
      this.mToken = String.format( "%.3f", temp ) ;
    } // else if
    
    else this.mType = Type.SYMBOL ;

  } // Classify()
  
  
  // �P�_�O�_��Integer
  boolean IsInt( String str ) {
    boolean isPositiveOrNegative = false ;
    
    for ( int i = 0 ; i < str.length() ; i++ ) {
      char ch = str.charAt( i ) ;

      // �P�_+��-�Ÿ��O���O���X�{�L�H�άO���O�X�{�b�Ĥ@�Ӧr����m
      // �p�G�����ƥX�{�Τ��O�X�{�b�Ĥ@�Ӧr����m�Nreturn false
      if ( ch == '+' || ch == '-' ) {
        if ( isPositiveOrNegative ) return false ;
        else if ( i != 0 ) return false ;
        else if ( i == 0 ) isPositiveOrNegative = true ;
      } // if
      
      else if ( ch == '.' ) return false ;
      
      else if ( ! Character.isDigit( ch ) ) return false ;
    } // for
    
    return true ;
  } // IsInt()
  
  
  // �P�_�O�_��Float
  boolean IsFloat( String str ) {
    boolean isPositiveOrNegative = false ;
    boolean hasDot = false ;
    
    for ( int i = 0 ; i < str.length() ; i++ ) {
      char ch = str.charAt( i ) ;

      // �P�_+��-�Ÿ��O���O���X�{�L�H�άO���O�X�{�b�Ĥ@�Ӧr����m
      // �p�G�����ƥX�{�Τ��O�X�{�b�Ĥ@�Ӧr����m�Nreturn false
      if ( ch == '+' || ch == '-' ) {
        if ( isPositiveOrNegative ) return false ;
        else if ( i != 0 ) return false ;
        else if ( i == 0 ) isPositiveOrNegative = true ;
      } // if
      
      // �p�G���ƥX�{ . �Nreturn false
      else if ( ch == '.' ) {
        if ( hasDot ) return false ;
        else hasDot = true ;
      } // else if
      
      else if ( ! Character.isDigit( ch ) ) return false ;
    } // for
    
    return true ;
  } // IsFloat()

} // class Token
