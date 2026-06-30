let alunoSelecionado = null;
let alunoEditando = null;

// funcao para cadstrar novo aluno
async function cadastrarAluno() {
    const formCadastrar = document.getElementById("form-aluno");
    const messageCadastrar = document.getElementById("messageCadastrar");

    formCadastrar.addEventListener("submit", async (e) => {
        e.preventDefault();

        messageCadastrar.innerText = ``;
        messageCadastrar.className = ``;

        try {
            const formData = new FormData(formCadastrar);

            // validacao para remover espacos no inicio e final da string
            for (let [key, value] of formData.entries()) {
                if (typeof value === "string") {
                    formData.set(key, value.trim());
                }
            }

            const data = Object.fromEntries(formData.entries());

            const response = await fetch("/alunos", {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            });

            if (!response.ok) {
                throw new Error("Deu erro");
            }

            messageCadastrar.innerText = `Aluno cadastrado com sucesso!`;
            messageCadastrar.className = `success`;

            formCadastrar.reset();
            await carregarAlunos();
        } catch (error) {
            console.log(error)
        }
    })
}

// funcao para carregar a tabela de alunos
async function carregarAlunos() {
    try {
        const response = await fetch("/alunos", {
            method: "GET"
        });

        if (!response.ok) {
            throw new Error("Deu erro");
        }

        const alunos = await response.json();

        const tbodyAlunos = document.getElementById("tabela-alunos-body");
        tbodyAlunos.innerHTML = "";

        const alunosFragment = document.createDocumentFragment();

        alunos.forEach(aluno => {
            const tr = document.createElement("tr");

            tr.innerHTML = `
                <td>${aluno.id}</td>
                <td>${aluno.nome}</td>
                <td>${aluno.dataNascimento}</td>
                <td>${aluno.email}</td>
                <td>${aluno.telefone}</td>
                <td>
                </td>
            `;

            const tdAcoes = tr.querySelector("td:last-child");

            const btnExcluir = document.createElement("button");
            btnExcluir.textContent = "Deletar";
            btnExcluir.classList.add("btn");
            btnExcluir.classList.add("btn-small");
            btnExcluir.classList.add("btn-danger");

            btnExcluir.addEventListener("click", () => {
                abrirModalExclusao(aluno);
            });

            const btnEditar = document.createElement("button");
            btnEditar.textContent = "Editar";
            btnEditar.classList.add("btn");
            btnEditar.classList.add("btn-small");
            btnEditar.classList.add("btn-primary");

            btnEditar.addEventListener("click", () => {
                abrirModalEdicao(aluno);
            });

            tdAcoes.appendChild(btnExcluir);
            tdAcoes.appendChild(btnEditar);

            alunosFragment.appendChild(tr);
        });

        tbodyAlunos.appendChild(alunosFragment);
    } catch (error) {
        console.log(error);
    }
}

// funcoes modal de exclusao
function abrirModalExclusao(aluno) {
    alunoSelecionado = aluno;

    document.getElementById("modal-nome-aluno").textContent = aluno.nome;

    const modal = document.getElementById("modal-confirmacao");
    modal.hidden = false;
}

function fecharModalExclusao() {
    alunoSelecionado = null;

    const modal = document.getElementById("modal-confirmacao");
    modal.hidden = true;
}

async function excluirAluno() {
    if (!alunoSelecionado) return;

    try {

        const response = await fetch(`/alunos/${alunoSelecionado.id}`, {
            method: "DELETE"
        });

        if (!response.ok) {
            throw new Error("Erro ao excluir aluno");
        }

        fecharModalExclusao();

        await carregarAlunos();

    } catch (error) {
        console.error(error);
    }
}

function configurarModal() {
    document.getElementById("btn-cancelar-exclusao").addEventListener("click", fecharModalExclusao);
    document.getElementById("btn-confirmar-exclusao").addEventListener("click", excluirAluno);
}

// funcoes modal de edicao
function abrirModalEdicao(aluno) {
    alunoEditando = aluno;

    document.getElementById("edit-id").value = aluno.id;
    document.getElementById("edit-nome").value = aluno.nome;
    document.getElementById("edit-data").value = aluno.dataNascimento;
    document.getElementById("edit-email").value = aluno.email;
    document.getElementById("edit-telefone").value = aluno.telefone;

    document.getElementById("modal-edicao").hidden = false;
}

function fecharModalEdicao() {
    alunoEditando = null;
    document.getElementById("modal-edicao").hidden = true;
}

async function atualizarAluno(e) {
    e.preventDefault();

    try {
        const data = {
            id: document.getElementById("edit-id").value,
            nome: document.getElementById("edit-nome").value.trim(),
            dataNascimento: document.getElementById("edit-data").value,
            email: document.getElementById("edit-email").value.trim(),
            telefone: document.getElementById("edit-telefone").value.trim()
        };

        const response = await fetch(`/alunos/${data.id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        });

        if (!response.ok) {
            throw new Error("Erro ao atualizar aluno");
        }

        fecharModalEdicao();
        await carregarAlunos();

    } catch (error) {
        console.error(error);
    }
}

function configurarModalEdicao() {
    document.getElementById("btn-cancelar-edicao").addEventListener("click", fecharModalEdicao);
    document.getElementById("form-edicao").addEventListener("submit", atualizarAluno);
}


document.addEventListener("DOMContentLoaded", () => {
    carregarAlunos();
    cadastrarAluno();
    configurarModal();
    configurarModalEdicao();
})