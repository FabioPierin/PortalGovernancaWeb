var apps = "";



function loadApplicationsFromServer(){
	$.ajax({
		headers: { 
			"Content-Type": "application/json; charset=utf-8;",
			"Accept": "application/json; charset=utf-8;"
		},
		url: "/PGW/applications/all",
		type: "GET",
		dataType: "json",
		success: function (data){
			apps = data.apps;
			
			
			
			const applications = apps.map((app) =>
			<tr>
			<td>{app.status}</td>
			<td>{app.name}</td>
			<td>{app.date}</td>
			<td>{app.description}</td>
			<td>{app.url}</td>
			<td>{app.console}</td>
			</tr>
			);
			const dados = {
					title : 'Portal de Governança'
			};
			
			
			var ElemTitle = (
					<h1>{dados.title}</h1>
			);	
			
			const headerTable = (
					<tr>
					<th>Status</th>
					<th>Nome aplicação</th>
					<th>Data ativação/desativação</th>
					<th>Descrição</th>
					<th>URL de acesso</th>
					<th>Console administrativo</th>
					</tr>
			);
			
			const manageServers  = (
					<div>
					<center>
					<a href="servidores" class="button">Gerenciar servidores</a>
					</center>
					</div>
			); 
			
			
			
			
			const content = (
					<div>  	
					{ElemTitle}
					<table>
					{headerTable}
					{applications}
					</table>
					{manageServers}
					</div>
			);
			ReactDOM.render(
					content,
					document.getElementById('root')
			);
			
		},
		error: function (data){
			alert($("#errorPost").html());
		}
	});
}



loadApplicationsFromServer();
ReactDOM.render(
  <div>LOADING...</div>,
  document.getElementById('root')
);
