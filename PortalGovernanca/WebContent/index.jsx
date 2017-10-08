
const dados = {
	title : 'Portal de Governança',
	welcomeText : 'Bem vindo ao Portal de Governança!' 
		+' Esse portal visa oferecer uma visualização rápida de todas as aplicações instaladas em uma lista de servidores de aplicação WebSphere. Juntamente com o nome da aplicação, é exibida outras imformações como,' 
		+' a data em que a aplicação foi inserida no servidor, o estatus atual da aplicação.'
};


var ElemTitle = (
	 <h1>{dados.title}</h1>
);	
	

const ElemParagraphy = (
 	<p>{dados.welcomeText}</p>
);

const ElemEnter = (
	<div class="button">
    	<a href="aplicacoes.html"> Entrar </a>
    </div>
);

const content = (
<div>  	
     {ElemTitle}
     {ElemParagraphy}
     {ElemEnter}
 </div>
 );

ReactDOM.render(
  content,
  document.getElementById('root')
);
