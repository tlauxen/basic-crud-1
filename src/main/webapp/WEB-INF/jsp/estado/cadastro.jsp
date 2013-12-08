<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="container" style="margin-top: 40px;">
	<div class="row-fluid">
		<div class="span12">

			<section>

				<div class="page-header">
					<h1>Cadastro de estados</h1>
				</div>
					
				<form class="form-horizontal well well-large">
					<div class="control-group">
						<label class="control-label" for="sigla">Sigla:</label>
						<div class="controls">
							<input type="text" id="sigla" placeholder="sigla" data-bind="value: model().sigla">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="nome">Nome</label>
						<div class="controls">
							<input type="text" id="nome" placeholder="Nome" data-bind="value: model().nome">
						</div>
					</div>
					
					<div class="control-group">
						<label class="control-label" for="nome">País</label>
						<div class="controls">
							<select data-bind="options: paises, optionsText: 'nome', value: pais, optionsCaption: 'Selecione...'"></select>
						</div>
					</div>
					
					<div class="control-group">
						<div class="controls">
							<div class="btn-group">
								<button type="submit" class="btn btn-default" data-bind="click: save">Gravar</button>
								<button type="button" class="btn btn-default" data-bind="click: cancel">Cancelar</button>
							</div>
						</div>
					</div>

				</form>

			</section>

		</div>
	</div>
</div>
