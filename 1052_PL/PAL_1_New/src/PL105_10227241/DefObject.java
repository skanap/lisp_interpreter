package PL105_10227241;

import java.util.Vector;

class DefObject {
  String mName ;
  String mType ;
  // function -> ����function
  // function_user -> Symbol : �Q�ϥΪ̩w�q������function
  // function_lambda -> �ϥΪ̦۩w�qfunction
  // symbol -> �ϥΪ̦۩w�qsymbol
  
  Node mBinding ;
  
  int mParameter ;
  //  9999 �� 0~�L�W��
  // 19999 �� 1~�L�W��
  // 29999 �� 2~�L�W��
  //    23 �� 2��3��
  
  String mParameter_Type ;
  
  Vector<DefObject> mFunction_Parameter ;        // lambda / define��
  Vector<Node> mFunction_Body ;                  // lambda / define��
  
  
  // Constructor : �ϥΪ̦۩w�qFunction
  DefObject( String name, String type, Node tree, int parameter, String p_type, 
             Vector<DefObject> f_para, Vector<Node> f_body ) {
    mName = name ;
    mType = type ;
    mBinding = tree ;
    mParameter = parameter ;
    mParameter_Type = p_type ;
    mFunction_Parameter = f_para ;
    mFunction_Body = f_body ;
  } // DefObject()
  
  
  // Constructor : ����Function
  DefObject( String name, String type, int parameter, String p_type ) {
    mName = name ;
    mType = type ;
    mParameter = parameter ;
    mParameter_Type = p_type ;
    
    Token t = new Token( "#<procedure " + name + ">", 0, 0 ) ;
    t.Classify() ;
    mBinding = new Node( t ) ;
    mFunction_Body = null ;
    mFunction_Parameter = null ;
  } // DefObject()
  
  
  // Constructor : �ϥΪ̦۩w�qSymbol
  DefObject( String name, String type, Node tree ) {
    mName = name ;
    mType = type ;
    mBinding = tree ;
    mParameter = 0 ;
    mParameter_Type = null ;
    mFunction_Body = null ;
    mFunction_Parameter = null ;
  } // DefObject()
  
  
  String GetName() {
    return mName ;
  } // GetName()
  
  void SetName( String name ) {
    mName = name ;
  } // SetName()
  
  
  String GetType() {
    return mType ;
  } // GetType()
  
  void SetType( String type ) {
    mType = type ;
  } // SetType()
  
  Node GetBinding() {
    return mBinding ;
  } // GetBinding()
  
  void SetBinding( Node node ) {
    mBinding = node ;
  } // SetBinding()
  
  int GetParameterNumber() {
    return mParameter ;
  } // GetParameterNumber()
  
  void SetParameterNumber( int num ) {
    mParameter = num ;
  } // SetParameterNumber()
  
  String GetParameterType() {
    return mParameter_Type ;
  } // GetParameterType()
  
  void SetParameterType( String p_type ) {
    mParameter_Type = p_type ;
  } // SetParameterType()
  
  void SetFunctionParameter( Vector<DefObject> para ) {
    mFunction_Parameter = para ;
  } // SetFunctionParameter()

  Vector<DefObject> GetFunctionParameter() {
    return mFunction_Parameter ;
  } // GetFunctionParameter()
  
  void SetFunctionBody( Vector<Node> body ) {
    mFunction_Body = body ;
  } // SetFunctionBody()

  Vector<Node> GetFunctionBody() {
    return mFunction_Body ;
  } // GetFunctionBody()
  
  
  static void CopyDefObject( DefObject new_d, DefObject old_d ) {
    new_d = new DefObject( old_d.GetName(), old_d.GetType(), old_d.GetBinding(),
                           old_d.GetParameterNumber(), old_d.GetParameterType(), 
                           old_d.GetFunctionParameter(), old_d.GetFunctionBody() ) ;

  } // CopyDefObject()
  
} // class DefObject
