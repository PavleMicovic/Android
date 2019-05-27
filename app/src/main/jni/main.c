#include "myJni.h"

JNIEXPORT jint JNICALL Java_com_example_mama_tvoja_MyNDK_calculate_temp
  (JNIEnv *env, jobject o, jdouble temp, jint ind){
   if(ind==0)//iz celzijusa u farenhajt
    return(int) (temp * (9 / 5) + 32);
    else{
    return temp;
    }

  }
