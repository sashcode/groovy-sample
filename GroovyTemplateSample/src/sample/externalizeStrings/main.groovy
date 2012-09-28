package sample.externalizeStrings

//---------------------------------------------------------------------------
//this script file path
def scriptFilePath =  this.class.protectionDomain.codeSource.location.file
//this script parent folder path
def folderPath = new File(scriptFilePath).parent;

//---------------------------------------------------------------------------
//load messages
def initMessage(folderPath){
	//check local
	def lang =  Locale.getDefault().getLanguage();
	File f = new File(folderPath + '/messages_' + lang + '.groovy')
	if(!f.exists()){
		//defaul messages
		f = new File(folderPath + '/messages.groovy')
	}
	assert f.exists();
	//load mesages
	evaluate(f.getText("UTF-8"))
}

//load messages
initMessage(folderPath)

//---------------------------------------------------------------------------
//use message
//  in template engine
def text = '''\
 a = <%=a%>
 b = <%=b%>
'''
def engine = new groovy.text.SimpleTemplateEngine()
def template = engine.createTemplate(text).make(messages)
File file = new File(folderPath + '/export.txt').write(template.toString());

//  in evaluated script file
evaluate(new File(folderPath + '/sub.groovy').getText("UTF-8"))