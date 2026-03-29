-- Seed data for Biblioteca Viva domain entities.
-- Important: this script assumes tables already exist (Hibernate `ddl-auto=create` or migrations).

-- =====================
-- Parent table (`Work`)
-- =====================
INSERT INTO obras (id, title, author, publication_date, description) VALUES
('11111111-1111-1111-1111-111111111001', 'Reportagem sobre o Rio Capibaribe', 'Ana Souza', '2024-03-10', 'Reportagem escolar sobre a historia e os desafios ambientais do Rio Capibaribe.'),
('11111111-1111-1111-1111-111111111002', 'Artigo: Literatura na Comunidade', 'Joao Carlos', '2024-05-01', 'Reflexao sobre o impacto de projetos de leitura em bairros perifericos.'),
('11111111-1111-1111-1111-111111111003', 'Materia Especial: Vozes da Escola', 'Marina Lima', '2024-08-15', 'Coletanea de entrevistas com estudantes sobre producao textual.'),

('22222222-2222-2222-2222-222222222001', 'Cordel da Feira Popular', 'Severino Alves', '2023-09-12', 'Cordel sobre personagens e historias da feira local.'),
('22222222-2222-2222-2222-222222222002', 'Cordel do Sertao Vivo', 'Rita Bezerra', '2024-01-19', 'Versos sobre memoria, resistencia e cultura do sertao.'),
('22222222-2222-2222-2222-222222222003', 'Cordel da Juventude', 'Paulo Nascimento', '2024-07-02', 'Narrativa em cordel sobre sonhos e trajetorias de jovens estudantes.'),

('33333333-3333-3333-3333-333333333001', 'Redacao ENEM: Inclusao Digital', 'Camila Araujo', '2024-04-05', 'Redacao dissertativa sobre acesso tecnologico e desigualdade social.'),
('33333333-3333-3333-3333-333333333002', 'Redacao ENEM: Sustentabilidade Urbana', 'Igor Mendes', '2024-06-10', 'Discussao sobre mobilidade, residuos e politicas publicas locais.'),
('33333333-3333-3333-3333-333333333003', 'Redacao ENEM: Leitura e Cidadania', 'Priscila Rocha', '2024-09-08', 'Argumentacao sobre bibliotecas escolares como espacos de transformacao.'),

('44444444-4444-4444-4444-444444444001', 'Conto da Janela Azul', 'Beatriz Lins', '2023-11-21', 'Conto curto sobre imaginacao e amizade durante as ferias.'),
('44444444-4444-4444-4444-444444444002', 'Conto da Chuva Mansa', 'Fabio Moura', '2024-02-14', 'Historia breve ambientada em uma tarde chuvosa no interior.'),
('44444444-4444-4444-4444-444444444003', 'Conto do Trem das Seis', 'Larissa Farias', '2024-10-01', 'Narrativa sobre encontros cotidianos e escolhas inesperadas.'),

('55555555-5555-5555-5555-555555555001', 'Lenda da Pedra Encantada', 'Vitoria Nogueira', '2023-12-30', 'Recontagem de narrativa popular com elementos fantasticos regionais.'),
('55555555-5555-5555-5555-555555555002', 'Conto Popular do Vento Norte', 'Marcelo Pires', '2024-03-18', 'Historia oral adaptada para linguagem escrita escolar.'),
('55555555-5555-5555-5555-555555555003', 'Fabula do Passaro Dourado', 'Julia Campos', '2024-08-03', 'Narrativa simbolica sobre coragem, cuidado e coletividade.'),

('66666666-6666-6666-6666-666666666001', 'Cartaz Digital: Semana da Leitura', 'Nicolas Batista', '2024-01-05', 'Peca visual produzida para divulgar atividades da semana cultural.'),
('66666666-6666-6666-6666-666666666002', 'Ilustracao: Jardim da Escola', 'Mirela Santos', '2024-04-12', 'Arte digital inspirada no espaco de convivencia da escola.'),
('66666666-6666-6666-6666-666666666003', 'Quadrinho Curto: Biblioteca Viva', 'Daniel Vieira', '2024-09-20', 'Historia em quadrinhos sobre o cotidiano da biblioteca escolar.'),

('77777777-7777-7777-7777-777777777001', 'Infografico: Ciclo da Agua', 'Helena Ramos', '2023-10-09', 'Infografico educativo explicando etapas do ciclo da agua.'),
('77777777-7777-7777-7777-777777777002', 'Infografico: Alimentacao Saudavel', 'Pedro Matos', '2024-05-23', 'Resumo visual com orientacoes de habitos alimentares.'),
('77777777-7777-7777-7777-777777777003', 'Infografico: Historia do Bairro', 'Renata Borges', '2024-11-11', 'Linha do tempo visual com marcos historicos da comunidade.'),

('88888888-8888-8888-8888-888888888001', 'Curta: Vozes da Juventude', 'Coletivo Audiovisual 8A', '2024-02-01', 'Curta-metragem escolar com depoimentos sobre futuro e educacao.'),
('88888888-8888-8888-8888-888888888002', 'Video-poema: Mar de Dentro', 'Turma 9B', '2024-06-28', 'Video-poema autoral com leitura dramatizada e trilha original.'),
('88888888-8888-8888-8888-888888888003', 'Entrevista: Mestres da Comunidade', 'Laboratorio de Midia', '2024-10-22', 'Serie de entrevistas com liderancas locais e artistas populares.'),

('99999999-9999-9999-9999-999999999001', 'Literatura em Libras: O Menino e a Lua', 'Grupo Libras 1', '2024-03-03', 'Narrativa sinalizada adaptada para publico infantojuvenil.'),
('99999999-9999-9999-9999-999999999002', 'Literatura em Libras: A Festa da Rua', 'Grupo Libras 2', '2024-07-17', 'Performance em Libras baseada em cronica comunitaria.'),
('99999999-9999-9999-9999-999999999003', 'Literatura em Libras: Pequenas Coragens', 'Grupo Libras 3', '2024-12-02', 'Narracao visual sobre autonomia e pertencimento.');

-- =========================
-- Textual concrete entities
-- =========================
INSERT INTO article (id, content) VALUES
('11111111-1111-1111-1111-111111111001', 'Texto jornalistico com introducao, desenvolvimento e conclusao sobre o rio.'),
('11111111-1111-1111-1111-111111111002', 'Artigo opinativo com dados de leitura e analise social.'),
('11111111-1111-1111-1111-111111111003', 'Materia especial com entrevistas e contexto historico local.');

INSERT INTO cordel (id, content, rhyme_scheme) VALUES
('22222222-2222-2222-2222-222222222001', 'Versos em sextilhas descrevendo cenas da feira.', 'ABABCC'),
('22222222-2222-2222-2222-222222222002', 'Cordel em linguagem popular sobre memoria regional.', 'AABBCC'),
('22222222-2222-2222-2222-222222222003', 'Poema narrativo celebrando experiencias estudantis.', 'ABABAB');

INSERT INTO essay (id, content, rate, theme, theme_description, feedback) VALUES
('33333333-3333-3333-3333-333333333001', 'Argumentacao sobre acesso digital e politicas inclusivas.', 920, 'Inclusao digital', 'Impactos da exclusao digital na formacao de jovens.', 'Boa articulacao de repertorio e proposta de intervencao consistente.'),
('33333333-3333-3333-3333-333333333002', 'Texto dissertativo sobre sustentabilidade nas cidades.', 880, 'Sustentabilidade urbana', 'Desafios de mobilidade e gestao de residuos em centros urbanos.', 'Estrutura solida; aprofundar repertorio sociocultural na conclusao.'),
('33333333-3333-3333-3333-333333333003', 'Defesa da leitura como direito e instrumento de cidadania.', 960, 'Leitura e cidadania', 'Relacao entre bibliotecas publicas, escola e participacao social.', 'Excelente progressao argumentativa e coesao textual.');

INSERT INTO short_story (id, content) VALUES
('44444444-4444-4444-4444-444444444001', 'Uma menina descobre que sua janela revela mundos imaginarios.'),
('44444444-4444-4444-4444-444444444002', 'Dois amigos percorrem a cidade sob chuva em busca de abrigo.'),
('44444444-4444-4444-4444-444444444003', 'Passageiros compartilham lembrancas no ultimo trem da tarde.');

INSERT INTO tale (id, content, genre) VALUES
('55555555-5555-5555-5555-555555555001', 'Narrativa tradicional sobre uma pedra com poderes ancestrais.', 'fantasia regional'),
('55555555-5555-5555-5555-555555555002', 'Historia oral adaptada de um velho conto de vento e viagem.', 'conto popular'),
('55555555-5555-5555-5555-555555555003', 'Fabula moral sobre um passaro que ensina cooperacao.', 'fabula');

-- ========================
-- Visual concrete entities
-- ========================
INSERT INTO art (id, url, duration) VALUES
('66666666-6666-6666-6666-666666666001', 'https://midia.bibliotecaviva.local/artes/cartaz-semana-leitura.png', 0),
('66666666-6666-6666-6666-666666666002', 'https://midia.bibliotecaviva.local/artes/jardim-escola.png', 0),
('66666666-6666-6666-6666-666666666003', 'https://midia.bibliotecaviva.local/artes/quadrinho-biblioteca-viva.pdf', 0);

INSERT INTO infographic (id, url, duration) VALUES
('77777777-7777-7777-7777-777777777001', 'https://midia.bibliotecaviva.local/infograficos/ciclo-agua.svg', 0),
('77777777-7777-7777-7777-777777777002', 'https://midia.bibliotecaviva.local/infograficos/alimentacao-saudavel.svg', 0),
('77777777-7777-7777-7777-777777777003', 'https://midia.bibliotecaviva.local/infograficos/historia-bairro.svg', 0);

-- =============================
-- Audiovisual concrete entities
-- =============================
INSERT INTO multimedia (id, url) VALUES
('88888888-8888-8888-8888-888888888001', 'https://midia.bibliotecaviva.local/videos/vozes-juventude.mp4'),
('88888888-8888-8888-8888-888888888002', 'https://midia.bibliotecaviva.local/videos/mar-de-dentro.mp4'),
('88888888-8888-8888-8888-888888888003', 'https://midia.bibliotecaviva.local/videos/mestres-comunidade.mp4');

INSERT INTO libra_literature (id, url) VALUES
('99999999-9999-9999-9999-999999999001', 'https://midia.bibliotecaviva.local/libras/menino-lua.mp4'),
('99999999-9999-9999-9999-999999999002', 'https://midia.bibliotecaviva.local/libras/festa-rua.mp4'),
('99999999-9999-9999-9999-999999999003', 'https://midia.bibliotecaviva.local/libras/pequenas-coragens.mp4');
