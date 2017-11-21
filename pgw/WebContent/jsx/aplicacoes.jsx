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
					<th>Data inclusão</th>
					<th>URL de acesso</th>
					<th>Console administrativo</th>
				</tr>
			</thead>
			<tbody>{varApplicationsTable}</tbody>
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
					<td className="status">{app.currentState}
						<div className="tooltip">
							<div id="triangle-left"></div>
							<p>Tempo disponível: {app.percentOnline}% {app.currentState == 'A' ? "" : <span> antes de última inativação </span>}</p>
							<p>{app.currentState == 'A' ?  <span className='ativo'>Ativo</span> : <span className='inativo'>Inativo</span>} desde {app.lastStatusChange}</p>
							<ul>
							{app.status.map((st) => 
								<li>{st.status == 'A' ? "Ativado em " : "Inativado em "} {st.changed_at} </li>)
							}
							</ul>
						</div>
					</td>
					<td>{app.name}</td>
					<td>{app.inclusionDate}</td>
					<td><a target="_blanc" href={'http://' + app.server.url + ':' + app.port + app.uri} >{app.server.url + ':' + app.port + app.uri}</a></td>
					<td><a target="_blanc" href={'http://' + app.server.url + ':' + app.server.adminPort + '/ibm/console'} >{app.server.url + ':' + app.server.adminPort + '/ibm/console'}</a></td>
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