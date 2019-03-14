package PL105_10227241;

class MyException extends Exception {
  int mCase = 0 ;
  // 1 -> EOF Error
  // 2 -> String Error : end of line
  // 3 -> Exit
  // 4 -> ERROR (unexpected token) : atom or '(' expected
  // 5 -> ERROR (unexpected token) : ')' expected
  
  
  // 6 -> 參數個數不符: ERROR (incorrect number of arguments) : function名稱
  // 7 -> 參數型態不符: ERROR (function名稱 with incorrect argument type) : Token
  // 8 -> 無法辨識的symbol: ERROR (unbound symbol) : Token
  // 9 -> 這不是function(非symbol): ERROR (attempt to apply non-function) : 此節點的pretty print
  // 10 -> 格式不符: ERROR (function名稱(全大寫) format) : 整棵樹的pretty print
  // 11 -> 非list: ERROR (non-list) : 整棵樹的pretty print
  // 12 -> define/clean-environment/exit在第一層以外的地方: ERROR (level of function名稱(全大寫))
  // 13 -> 除數為0: ERROR (division by zero) : /
  // 14 -> 沒有可回傳的值, if/cond : ERROR (no return value) : if/cond部分的pretty print
  
  // ----------------------- no return value error -----------------------
  // 20 -> 呼叫function時，傳入的參數evaluate後no return value: ERROR (unbound parameter) : <code of the actual parameter>
  // 21 -> IF / COND的測試條件evaluate後no return value: ERROR (unbound test-condition) : <code of the test-condition>
  // 22 -> AND / OR的測試條件evaluate後no return value: ERROR (unbound condition) : <code of the condition>
  // 23 -> DEFINE / LET / SET!中，要定義的東西evaluate後no return value: ERROR (no return value) : <code of the "to be assigned">
  // 24(直接用88丟到main去catch) -> 最普通的那種IF / COND的no return value: ERROR (no return value) : <code entered at the top level>
  // 25 -> 所要執行的function由LIST表達時，LIST evaluate後no return value: ERROR (no return value) : <pretty print form of ( 。。。 ) >
  // ----------------------- no return value error -----------------------
  
  // 87 -> define.cond.set!.let.lambda 的 格式不符 (包括attempting to redefine system primitive)
  // 88 -> if/cond的no return value丟到Evaluate去看是要輸出何種error msg
  
  // 120 -> 補完no return value error msg 為了不再被catch直接丟到main
  
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