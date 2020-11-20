```
//隐藏输入法
  InputMethodManager manager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
  manager.hideSoftInputFromWindow( dialog.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

kitlin
//                /**隐藏软键盘**/
//                val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                imm.hideSoftInputFromWindow((context as Activity).currentFocus?.windowToken,      InputMethodManager.HIDE_NOT_ALWAYS)

改成view 的

fun hideInput(){
        val imm = view!!.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0) //windowToken
    }

```