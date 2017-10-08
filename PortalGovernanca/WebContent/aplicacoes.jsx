
var apps = [
	{nome : 'aplicação 1',
	url : '10.9.8.7/app1',
	data : '20/08/2017',
	status : 'ativo',
	console : '10.9.8.7:6070/console',
	descricao : 'Descrição da aplicação 1'},
	{nome : 'aplicação 2',
	url : '10.9.8.77/app2',
	data : '20/08/2017',
	status : 'ativo',
	console : '10.9.8.77:6070/console',
	descricao : 'Descrição da aplicação 2'},
	{nome : 'aplicação 3',
	url : '10.9.88.7/app3',
	data : '20/08/2017',
	status : 'ativo',
	console : '10.9.88.7:6070/console',
	descricao : 'Descrição da aplicação 3'},
	{nome : 'aplicação 1',
	url : '10.99.8.7/app4',
	data : '20/08/2017',
	status : 'ativo',
	console : '10.99.8.7:6070/console',
	descricao : 'Descrição da aplicação 4'}
	];
	
const dados = {
	title : 'Portal de Governança'
};

var ElemTitle = (
	 <h1>{dados.title}</h1>
);	

const headerTable = (<tr>
						<th>Status</th>
						<th>Nome aplicação</th>
						<th>Data ativação/desativação</th>
						<th>Descrição</th>
						<th>URL de acesso</th>
						<th>Console administrativo</th>
					</tr>);
const applications = apps.map((app) =>
	 <tr>
	 	<td>{app.status}</td>
	 	<td>{app.nome}</td>
	 	<td>{app.data}</td>
	 	<td>{app.descricao}</td>
	 	<td>{app.url}</td>
	 	<td>{app.console}</td>
	 </tr>
);	
	



const content = (
<div>  	
	 {ElemTitle}
     <table>
		 {headerTable}
		 {applications}
	 </table>
 </div>
 );

ReactDOM.render(
  content,
  document.getElementById('root')
);
