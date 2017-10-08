var ElemTitle = React.createClass({
	render: function() {
		return <h1>{this.props.title}</h1>;
	}
});	
	

var ElemParagraphy = React.createClass({
  render: function() {
    return <p>(this.props.text}</p>;
  }
});

var ElemButton = React.createClass({
  render: function() {
    return <button>{this.props.textlabel}</button>;
  }
});

var ElemLabel = React.createClass({
   render: function() {
    return <div>{this.props.text}</div>;
  }
});

