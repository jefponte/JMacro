# JMacro
Customize macros para serem executadas diariamente. 
A ideia é possibilitar a criação de uma macro simples para ser executada diariamente.


# Arquivos de configuração. 

rotina.txt
	
	Lista de macros a serem realizadas. 
	Cada macro será identificada por uma hora e 
	minuto separados por ponto ".". Da seguinte forma
	12.35 e depois um sinal de igualdade e o nome do 
	arquivo que descreverá a macro.
	
	12.35=macro1.txt indica que às 12 horas e 35 minutos 
	será executada a macro descrita no arquivo macro1.txt. 

 
macro1.txt

	Arquivo com comandos que descrevem a macro. 

Lista de comandos possíveis para uma macro: 
	
	click(12,12) 		- Clica em um ponto (x,y).  
	digitar(texto) 		- Digita um texto.
	enter() 		- Tecla Enter.
	espera(1000) 		- Aguarda milisegundos.  
	maximizar()		- Maximiza janelas. 
	capturar()		- Pressiona PrintScream, capturando tela. 
	colar()			- Pressiona Control+V, colando. 
	altf4()			- Pressiona Alt+F4, fechando uma janela. 
	altf2()			- Pressiona Alt+F2
	tab()			- Pressiona tecla TAB. 
	esc()			- Pressiona tecla ESC. 