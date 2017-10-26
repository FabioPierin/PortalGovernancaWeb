
class Voltar  extends React.Component {
  constructor(props) {
    super(props);
  }
  
  render(){
    return (
        <div class="button">
            <a href="/PGW/servidores" class="button">Gerenciar servidores</a>
        </div>
    );
  }
}

var varApplicationsTable = "";

class LoadApplications extends React.Component {
 
  constructor(props) {
    super(props);

  }

  render() {
    return (
	<div>
		<h1>Portal de Governança</h1>
		<table>
			<tr>
				<th>Status</th>
				<th>Nome aplicação</th>
				<th>Data ativação/desativação</th>
				<th>Descrição</th>
				<th>URL de acesso</th>
				<th>Console administrativo</th>
			</tr>
		{varApplicationsTable}
		</table>
		<Voltar />
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
			var apps = data.apps;
			if (apps == null) {
				alert("Server returned null");
			}
			
			
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
			
			varApplicationsTable = applications;
			
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
getApplications();