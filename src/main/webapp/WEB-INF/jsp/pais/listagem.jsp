<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="container" style="margin-top: 60px;">
	<div class="row-fluid">
		<div class="span12">
			<section>

				<div class="page-header">
					<h1>Listagem de países</h1>
				</div>
					
				<div class="btn-group">
					<button type="button" class="btn btn-default" data-bind="click: novo">Novo</button>
				</div>
				
				<form class="form-horizontal well well-large">
					<div class="control-group">
						<label class="control-label" for="sigla">Sigla:</label>
						<div class="controls">
							<input type="text" id="sigla" placeholder="sigla" data-bind="value: filter().sigla">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="nome">Nome</label>
						<div class="controls">
							<input type="text" id="nome" placeholder="Nome" data-bind="value: filter().nome">
						</div>
					</div>
					
					<div class="control-group">
						<div class="controls">
							<div class="btn-group">
								<button type="submit" class="btn btn-default" data-bind="click: find">Buscar</button>
							</div>
						</div>
					</div>

				</form>
				
				<table class="table">
					<thead>
						<tr>
							<th>Sigla</th>
							<th>Nome</th>
							<th></th>
						</tr>
					</thead>
					<tbody data-bind="foreach: list">
						<tr>
							<td><a href="#" data-bind="text: sigla, click: $root.edit"></a></td>
							<td data-bind="text: nome"></td>
							<td><a href="#" data-bind="click: $root.remove">Remover</a></td>
						</tr>
					</tbody>
				</table>
			</section>
		</div>
	</div>
</div>
