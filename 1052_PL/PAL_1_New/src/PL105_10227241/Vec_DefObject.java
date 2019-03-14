package PL105_10227241;

import java.util.Vector;

class Vec_DefObject {
  Vector<DefObject> mVec ;
  
  Vec_DefObject() {
    mVec = new Vector<DefObject>() ;
  } // Vec_DefObject()
  
  Vector<DefObject> GetVec() {
    return mVec ;
  } // GetVec()
  
  void SetVec( Vector<DefObject> v ) {
    mVec = v ;
  } // SetVec()

} // class Vec_DefObject
