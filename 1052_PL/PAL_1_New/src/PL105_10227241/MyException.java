package PL105_10227241;

class MyException extends Exception {
  int mCase = 0 ;
  // 1 -> EOF Error
  // 2 -> String Error : end of line
  // 3 -> Exit
  // 4 -> ERROR (unexpected token) : atom or '(' expected
  // 5 -> ERROR (unexpected token) : ')' expected
  
  
  // 6 -> �ѼƭӼƤ���: ERROR (incorrect number of arguments) : function�W��
  // 7 -> �Ѽƫ��A����: ERROR (function�W�� with incorrect argument type) : Token
  // 8 -> �L�k���Ѫ�symbol: ERROR (unbound symbol) : Token
  // 9 -> �o���Ofunction(�Dsymbol): ERROR (attempt to apply non-function) : ���`�I��pretty print
  // 10 -> �榡����: ERROR (function�W��(���j�g) format) : ��ʾ�pretty print
  // 11 -> �Dlist: ERROR (non-list) : ��ʾ�pretty print
  // 12 -> define/clean-environment/exit�b�Ĥ@�h�H�~���a��: ERROR (level of function�W��(���j�g))
  // 13 -> ���Ƭ�0: ERROR (division by zero) : /
  // 14 -> �S���i�^�Ǫ���, if/cond : ERROR (no return value) : if/cond������pretty print
  
  // ----------------------- no return value error -----------------------
  // 20 -> �I�sfunction�ɡA�ǤJ���Ѽ�evaluate��no return value: ERROR (unbound parameter) : <code of the actual parameter>
  // 21 -> IF / COND�����ձ���evaluate��no return value: ERROR (unbound test-condition) : <code of the test-condition>
  // 22 -> AND / OR�����ձ���evaluate��no return value: ERROR (unbound condition) : <code of the condition>
  // 23 -> DEFINE / LET / SET!���A�n�w�q���F��evaluate��no return value: ERROR (no return value) : <code of the "to be assigned">
  // 24(������88���main�hcatch) -> �̴��q������IF / COND��no return value: ERROR (no return value) : <code entered at the top level>
  // 25 -> �ҭn���檺function��LIST��F�ɡALIST evaluate��no return value: ERROR (no return value) : <pretty print form of ( �C�C�C ) >
  // ----------------------- no return value error -----------------------
  
  // 87 -> define.cond.set!.let.lambda �� �榡���� (�]�Aattempting to redefine system primitive)
  // 88 -> if/cond��no return value���Evaluate�h�ݬO�n��X���error msg
  
  // 120 -> �ɧ�no return value error msg ���F���A�Qcatch�������main
  
  public MyException() { super(); }
  
  public MyException( String message ) { super( message ) ; }
  
  public MyException( String message, Throwable cause ) { super( message, cause ) ; }
  
  public MyException( Throwable cause ) { super( cause ) ; }
  
  public MyException( String message, int iCase ) {
    super( message ) ;
    this.mCase = iCase ;
  } // MyException()

  int GetCase() {
    return this.mCase ;
  } // GetCase()

} // class MyException