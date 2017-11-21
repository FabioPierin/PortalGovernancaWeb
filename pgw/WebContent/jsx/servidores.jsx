var varServersTable = "";
var loaded = false;

function addServer(dataPost){
	$.ajax({
		headers: { 
	        "Content-Type": "application/json; charset=utf-8;",
        	"Accept": "application/json; charset=utf-8;"
	    },
	    url: "/PGW/servers/add",
	    type: "POST",
	    dataType: "json",
	    data: dataPost,
	    success: function (data){
	    	if(data.included){
	    		getServers();
	    	} else {
	    		alert("Servidor não incluido");
	    	}
		},
		error: function (data){
			alert("Erro ao tentar incluir!");
		}
	});
	
}

class NewServer extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
    		serverName: '',
    		url:'',
    		port:'',
    		user:'',
    		password:''
    				};

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange(event) {this.setState({value: event.target.value});

	  if(event.target.id == "serverName"){
      this.state.serverName = event.target.value;
      this.setState(this.state);
    }
    if(event.target.id == "url"){
      this.state.url = event.target.value;
      this.setState(this.state);
    }
    if(event.target.id == "port"){
      this.state.port = event.target.value;
      this.setState(this.state);
    }
    if(event.target.id == "user"){
      this.state.user = event.target.value;
      this.setState(this.state);
    }
    if(event.target.id == "password"){
      this.state.password = event.target.value;
      this.setState(this.state);
    }
  }

  handleSubmit(event) {
    if(this.state.serverName == ""){
      alert("Preencher 'Nome do servidor'");
    }else
    if(this.state.url == ""){
      alert("Preencher 'URL'");;
    }else
    if(this.state.port == ""){
      alert("Preencher 'Porta'");
    }else
    if(this.state.user == ""){
      alert("Preencher 'usuário'");
    }else {
    	var dataPost = '{' 
    			+'"serverName" : "' + this.state.serverName + '",'
    			+'"url" : "' +  this.state.url + '",'
    			+'"port" : "' +  this.state.port + '",'
    			+'"user" : "' +  this.state.user + '",'
    			+'"password" : "' +  this.state.password + '"'
    			+'}';
      addServer(dataPost);
    }
    event.preventDefault();
  }

  render() {
    return (
      
      	<tr>
        
          <td><input id="serverName" type="text" value={this.state.serverName} onChange={this.handleChange} /></td>
          <td><input id="url" type="text" value={this.state.url} onChange={this.handleChange} /></td>
          <td><input id="port" type="text" value={this.state.port} onChange={this.handleChange} /></td>
          <td><input id="user" type="text" value={this.state.user} onChange={this.handleChange} /></td>
          <td><input id="password" type="text" value={this.state.password} onChange={this.handleChange} />
        <form onSubmit={this.handleSubmit}> <input type="submit" value="Incluir" /> </form></td>
        
        </tr>
          
    );
  }
}


class Voltar  extends React.Component {
  constructor(props) {
    super(props);
  }
  
  render(){
    return (
        <div className="button">
            <a href="/PGW" className="button">Voltar</a>
        </div>
    );
  }
}



class LoadServers extends React.Component {
 
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
          <h1>Servidores monitorados</h1>
          { loading }
	      <table>
	      	  <thead>
			      <tr>
			        <th>Nome do servidor</th>
			        <th>URL</th>
			        <th>Porta SOAP</th>
			        <th>Usuario</th>
			        <th>Senha</th>
			      </tr>
		      </thead>
		      <tbody>
			      {varServersTable}
			      <NewServer />
		      </tbody>
	      </table>
	      <Voltar />
      </div>
    );
  }
}

function getServers(){
	$.ajax({
		headers: { 
			"Content-Type": "application/json; charset=utf-8;",
			"Accept": "application/json; charset=utf-8;"
		},
		url: "/PGW/servers/getAll",
		type: "GET",
		dataType: "json",
		success: function (data){
			var servers = data.serversList;
			if (servers == null) {
				alert(data.error);
			}else {
				const serversTable = servers.map((server) =>
				<tr key={server.id.toString()}>
					<td>{server.serverName}</td>
					<td>{server.url}</td>
					<td>{server.port}</td>
					<td>{server.user}</td>
					<td>{server.password}</td>
					<td><a href="#" onClick="alert()" >excluir</a></td>
				</tr>
				);
				
				varServersTable = serversTable;
			
			}
			loaded = true;
			ReactDOM.render(
					<LoadServers />,
					document.getElementById('root')
			);
		},
		error: function (data){
			alert("Erro ao carregar dados!");
		}
	});

}

getServers();

ReactDOM.render(
		  <LoadServers />,
		  document.getElementById('root')
		);