# Android-utils

>
> > PDF   itextpdf     【DocumentFactory  PdfWriter TempRhPdfSource 】
>
> > CollectionsUtil     【集合工具类 index向前移动 向后移动】
>
> > DateUtil         【日期类工具】
>
> > DbOperator       【数据库管理类】      
>
> > DeviceUtils            【App相关辅助类】
>
> > EncryptUtil            【MD5加密】
>
> > ExcelUtils          【EXEL CSV相关】
>
> > FileUtil         【文件操作工具类】
>
> > GsonFatory             【创建特定要求的Gson对象 com.google.gson】
>
> > IsUtils             【比较判断的方法】
>
> > IsUtils             【比较判断的方法】
>
> > Lg             【Log统一管理类】
>
> > NfcConfigure             【Nfc配置类】
>
> > StringUtils             【字符串工具类】
>
> > TimeStringUtil             【时间处理】
>
> > ToastUtil             【提示】
>
> > WindowUtils             【输入键盘相关】

#### 输入键盘相关更改
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




> 下列工具类  来自 https://github.com/WecanStudio/Android
>
> > ActivityManager     【activity的管理类】
>
> > AppUtils            【App相关辅助类】
>
> > DensityUtil         【dp和px单位转换】
>
> > KeyBoardUtils       【输入键盘相关】      
>
> > LogUtils            【日志管理】
>
> > MD5Utils            【MD5加密】
>
> > ScreenUtils         【屏幕相关】
>
> > SDCardUtils         【SD卡相关】
>
> > SPUtils             【缓存工具类】