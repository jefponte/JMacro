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

	Arquivo que descreve a macro. 
	lista de comandos possíveis. 
	
		click(12,12)
		digitar(texto)
		enter()
		maximizar()
		capturar()
		colar()
		altf4()
		altf2() 
	
	