  
* =====================================================
   AGRINHO 2026 - SCRIPT.JS
   Tecnologia, Sustentabilidade e IA no Campo
===================================================== */

document.addEventListener("DOMContentLoaded", () => {

    /* ==========================================
       TAMANHO DA FONTE
    ========================================== */

    let fontSize = 100;

    const aumentarFonte = document.getElementById("aumentarFonte");
    const diminuirFonte = document.getElementById("diminuirFonte");

    if (aumentarFonte) {
        aumentarFonte.addEventListener("click", () => {
            fontSize += 10;
            document.body.style.fontSize = `${fontSize}%`;
        });
    }

    if (diminuirFonte) {
        diminuirFonte.addEventListener("click", () => {
            if (fontSize > 70) {
                fontSize -= 10;
                document.body.style.fontSize = `${fontSize}%`;
            }
        });
    }

    /* ==========================================
       MODO ESCURO / CLARO
    ========================================== */

    const temaBtn = document.getElementById("alternarTema");

    if (temaBtn) {
        temaBtn.addEventListener("click", () => {
            document.body.classList.toggle("dark-mode");
        });
    }

    /* ==========================================
       LEITURA POR VOZ
    ========================================== */

    const btnLer = document.getElementById("lerConteudo");
    const btnParar = document.getElementById("pararLeitura");

    if (btnLer) {
        btnLer.addEventListener("click", () => {

            speechSynthesis.cancel();

            const conteudoPrincipal =
                document.getElementById("conteudo-principal");

            if (!conteudoPrincipal) return;

            const texto = conteudoPrincipal.innerText;

            const fala = new SpeechSynthesisUtterance(texto);

            fala.lang = "pt-BR";
            fala.rate = 1;
            fala.pitch = 1;
            fala.volume = 1;

            speechSynthesis.speak(fala);
        });
    }

    if (btnParar) {
        btnParar.addEventListener("click", () => {
            speechSynthesis.cancel();
        });
    }

    /* ==========================================
       ACCORDION
    ========================================== */

    const accordions =
        document.querySelectorAll(".accordion-btn");

    accordions.forEach(botao => {

        botao.addEventListener("click", () => {

            botao.classList.toggle("ativo");

            const painel =
                botao.nextElementSibling;

            if (
                painel.style.maxHeight
            ) {
                painel.style.maxHeight = null;
            } else {
                painel.style.maxHeight =
                    painel.scrollHeight + "px";
            }
        });

    });

    /* ==========================================
       FORMULÁRIO DE INSCRIÇÃO
    ========================================== */

    const formulario =
        document.getElementById("formInscricao");

    if (formulario) {

        formulario.addEventListener("submit", (e) => {

            e.preventDefault();

            const nome =
                document.getElementById("nome").value;

            alert(
                `Obrigado pela inscrição, ${nome}! 🌱`
            );

            formulario.reset();

        });

    }

    /* ==========================================
       ÁREA DE COMENTÁRIOS
    ========================================== */

    const enviarComentario =
        document.getElementById("enviarComentario");

    if (enviarComentario) {

        enviarComentario.addEventListener("click", () => {

            const campo =
                document.getElementById("comentario");

            const lista =
                document.getElementById("listaComentarios");

            const texto = campo.value.trim();

            if (texto === "") {
                alert(
                    "Digite um comentário antes de enviar."
                );
                return;
            }

            const comentario =
                document.createElement("div");

            comentario.classList.add("comentario");

            comentario.innerHTML = `
                <p>${texto}</p>
                <small>
                    Publicado agora
                </small>
            `;

            lista.prepend(comentario);

            campo.value = "";

        });

    }

    /* ==========================================
       ANIMAÇÃO AO ROLAR
    ========================================== */

    const elementos =
        document.querySelectorAll(".revelar");

    const observador =
        new IntersectionObserver(
            (entradas) => {

                entradas.forEach((entrada) => {

                    if (entrada.isIntersecting) {
                        entrada.target.classList.add(
                            "ativo"
                        );
                    }

                });

            },
            {
                threshold: 0.15
            }
        );

    elementos.forEach((elemento) => {
        observador.observe(elemento);
    });

    /* ==========================================
       SCROLL SUAVE
    ========================================== */

    const links =
        document.querySelectorAll('a[href^="#"]');

    links.forEach(link => {

        link.addEventListener("click", (e) => {

            e.preventDefault();

            const destino =
                document.querySelector(
                    link.getAttribute("href")
                );

            if (destino) {

                destino.scrollIntoView({
                    behavior: "smooth"
                });

            }

        });

    });

});



  