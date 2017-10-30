var loaded = false;

var varApplicationsTable = "";

class ManageServer  extends React.Component {
  constructor(props) {
    super(props);
  }
  
  render(){
    return (
        <div className="button">
            <a href="/PGW/servidores" className="button">Gerenciar servidores</a>
        </div>
    );
  }
}

class LoadApplications extends React.Component {
 
  constructor(props) {
    super(props);

  }

  render() {
	  let loading;
	  if (!loaded){
		  loading = (
			  <div className="loading">
			  	<img src="/PGW/css/images/loading.gif" />
			  </div>
		  ) 
	  }
    return (
	<div>
		{ loading }
		<h1>Portal de Governança</h1>
		<table>
			<thead>
				<tr>
					<th>Status</th>
					<th>Nome aplicação</th>
					<th>Data ativação/desativação</th>
					<th>Descrição</th>
					<th>URL de acesso</th>
					<th>Console administrativo</th>
				</tr>
			</thead>
			<tbody>
				{varApplicationsTable}
			</tbody>
		</table>
		<ManageServer />
	</div>
    );
  }
}

function getApplications(){
	$.ajax({
		headers: { 
			"Content-Type": "application/json; charset=utf-8;",
			"Accept": "application/json; charset=utf-8;"
		},
		url: "/PGW/applications/all",
		type: "GET",
		dataType: "json",
		success: function (data){
			var apps = data.appList;
			if (apps == null) {
				alert(data.error);
			}else {
				const applications = apps.map((app) =>
				<tr key={app.id.toString()}>
					<td>{app.currentState}</td>
					<td>{app.name}</td>
					<td>{app.inclusionDate}</td>
					<td>{app.description}</td>
					<td><a target="_blanc" href='{app.server.url}:{app.port}{app.uri}'>{app.server.url}:{app.port}{app.uri}</a></td>
					<td><a target="_blanc" href='{app.server.url}:{app.server.port}'>{app.server.url}:{app.server.port}</a></td>
				</tr>
				);
				
				varApplicationsTable = applications;
			}
			loaded = true;
			ReactDOM.render(
					<LoadApplications />,
					document.getElementById('root')
			);
		},
		error: function (data){
			alert("Erro ao carregar dados!");
		}
	});

}
ReactDOM.render(
		<LoadApplications />,
		document.getElementById('root')
);
getApplications();