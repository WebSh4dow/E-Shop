create or replace function validaChavePessoa()
	returns  trigger
	language plpgsql
as $$
declare existe integer;

begin
	existe = (select count(1) from pessoa_fisica where id = NEW.pessoa_id);
	if(existe <= 0 ) then
	 existe = (select count (1) from pessoa_juridica where id = new.pessoa_id);
	if(existe <= 0) then
		raise exception 'Não foi encontrado o id ou PK da pessoa para realizar a associação.';
	 end if;
	end if;
end;
$$

create trigger validaChavePessoaAvaliacaoProduto
before update
on avaliacao_produto
for each row
execute procedure validaChavePessoa();

create trigger validaChavePessoaAvaliacaoProduto2
before insert
on avaliacao_produto
for each row
execute procedure validaChavePessoa();


create trigger validaChavePessoaContasPagar_forn_id
before update
on conta_pagar
for each row
execute procedure validaChavePessoa();

create trigger validaChavePessoaContasPagar_forn_id2
before insert
on conta_pagar
for each row
execute procedure validaChavePessoa();


create trigger validaChavePessoaContasReceber_forn_id
before update
on conta_receber
for each row
execute procedure validaChavePessoa();

create trigger validaChavePessoaContasReceber_forn_id2
before insert
on conta_receber
for each row
execute procedure validaChavePessoa();



create trigger validaChavePessoaEndereco_forn_id
before update
on endereco
for each row
execute procedure validaChavePessoa();

create trigger validaChavePessoaEndereco_forn_id2
before insert
on endereco
for each row
execute procedure validaChavePessoa();


create trigger validaChavePessoaNotaFiscalCompra
before update
on nota_fiscal_compra
for each row
execute procedure validaChavePessoa();

create trigger validaChavePessoaNotaFiscalCompra2
before insert
on nota_fiscal_compra
for each row
execute procedure validaChavePessoa();


create trigger validaChavePessoaUsuario
before update
on usuario
for each row
execute procedure validaChavePessoa();

create trigger validaChavePessoaUsuario2
before insert
on usuario
for each row
execute procedure validaChavePessoa();


create trigger validaChaveVendaLojaVirt
before update
on vd_cp_loja_virt
for each row
execute procedure validaChavePessoa();

create trigger validaChaveVendaLojaVirt2
before insert
on vd_cp_loja_virt
for each row
execute procedure validaChavePessoa();


