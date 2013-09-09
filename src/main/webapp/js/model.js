//Entity (must be like java pojo)
function Pais(data) {
    this.id = ko.observable(data.id);
    this.sigla = ko.observable(data.sigla);
    this.nome = ko.observable(data.nome);
}

//Entity (must be like java pojo)
function Estado(data) {
    this.id = ko.observable(data.id);
    this.pais = ko.observable(data.pais);
    this.sigla = ko.observable(data.sigla);
    this.nome = ko.observable(data.nome);
}

