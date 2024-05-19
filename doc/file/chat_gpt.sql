/*
 Navicat Premium Data Transfer

 Source Server         : [测试]
 Source Server Type    : MySQL
 Source Server Version : 50740 (5.7.40-log)
 Source Host           : 127.0.0.1:3306
 Source Schema         : chat_gpt

 Target Server Type    : MySQL
 Target Server Version : 50740 (5.7.40-log)
 File Encoding         : 65001

 Date: 13/05/2024 15:35:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gpt_agreement
-- ----------------------------
DROP TABLE IF EXISTS `gpt_agreement`;
CREATE TABLE `gpt_agreement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `title` varchar(250) NOT NULL DEFAULT '' COMMENT '标题',
  `type` smallint(6) DEFAULT '0' COMMENT '类型',
  `status` smallint(6) DEFAULT '0' COMMENT '状态 0 禁用 1 启用',
  `content` longtext COMMENT '内容',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='内容管理';

-- ----------------------------
-- Records of gpt_agreement
-- ----------------------------
BEGIN;
INSERT INTO `gpt_agreement` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `title`, `type`, `status`, `content`, `deleted`) VALUES (1, 'System', '2023-05-04 03:09:28', 'admin', '2024-05-13 15:34:25', '用户协议', 1, 1, '', 0);
INSERT INTO `gpt_agreement` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `title`, `type`, `status`, `content`, `deleted`) VALUES (2, 'System', '2023-05-04 03:09:28', 'admin', '2024-05-13 15:34:28', '隐私政策', 2, 1, '', 0);
INSERT INTO `gpt_agreement` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `title`, `type`, `status`, `content`, `deleted`) VALUES (3, 'System', '2023-05-04 03:09:28', 'System', '2023-05-04 03:09:28', '【知识扫盲】什么是GPT？它有什么特点？', 3, 0, '<p style=\"text-align: justify; line-height: 2;\">一、什么是GPT</p>\n<p style=\"text-align: justify; line-height: 2;\">GPT是一种基于人工智能技术的语言模型，它的全称是Generative Pre-trained Transformer。GPT的背景和发展历程可以追溯到2018年，由OpenAI公司提出，其目标是通过预先训练模型来增强自然语言处理（NLP）的性能。</p>\n<p style=\"text-align: justify; line-height: 2;\">简单来说，他的实现方式是将海量的文本语料，直接喂给模型进行学习，在这其中模型对词性、句法的学习自然而然会沉淀在模型的参数当中&hellip;&hellip;也就是经过预训练出一个这样的大语言模型后，AI理解了人类对语言的使用技巧（句法、语法、词性等），也理解了各种事实知识，甚至还懂得了代码编程，并最终在这样的一个大语言模型的基础上，直接降维作用于垂直领域的应用（例如闲聊对话，代码生成，文章生成等）。</p>\n<p style=\"text-align: justify; line-height: 2;\">GPT模型的特点在于其使用了Transformer架构。Transformer是一种基于自注意力机制的深度学习模型，能够高效地学习输入序列之间的关系，因此在处理自然语言时非常有效。GPT模型通过大规模的预训练来提高其性能，这样可以在训练后对其进行微调，以适应特定的NLP任务。</p>\n<p style=\"text-align: justify; line-height: 2;\">GPT模型被广泛用于各种自然语言处理任务，例如文本生成、文本分类、问答系统、机器翻译等等。GPT-3是目前最大的GPT模型，其参数量高达1750亿个，可以处理非常复杂的NLP任务。此外，GPT还可以用于文本自动摘要、文本纠错、情感分析等应用。</p>\n<p style=\"text-align: justify; line-height: 2;\">在传统的自然语言处理技术中，我们需要手动设计和提取特征来让计算机理解和处理自然语言。但这种方法很难应对不同的语言和任务，并且需要大量的人工劳动和经验。因此，研究人员开始探索使用深度学习来解决自然语言处理的问题。</p>\n<p style=\"text-align: justify; line-height: 2;\">Transformer是一种特殊的深度学习模型，它使用了自注意力机制来学习输入序列之间的关系。自注意力机制可以让模型高效地处理输入序列中的不同部分之间的相互作用，从而提高模型的性能。在自然语言处理任务中，Transformer模型可以很好地学习词汇之间的关系，从而更好地处理语言。</p>\n<p style=\"text-align: justify; line-height: 2;\">GPT的特别之处在于，它使用了预训练技术来提高模型的性能。预训练是指在大规模数据上训练模型，以提高其性能和泛化能力。在GPT中，研究人员使用了大量的无标签文本数据来预训练模型，从而让它学习到更多的语言知识和规律。预训练后，GPT可以在各种自然语言处理任务上进行微调，以适应不同的应用场景。</p>\n<p style=\"text-align: justify; line-height: 2;\">二、GPT的特点及如何应用</p>\n<ol>\n<li style=\"text-align: justify; line-height: 2;\">大规模：GPT-3是目前最大的预训练语言生成模型，拥有超过175B参数。</li>\n<li style=\"text-align: justify; line-height: 2;\">高效：GPT-3使用了最先进的技术和硬件来进行训练，从而在生成高质量的文本的同时，保证了计算效率。</li>\n<li style=\"text-align: justify; line-height: 2;\">通用：GPT-3可以进行多种自然语言任务，如文本生成，语音识别，问答等，具有较强的通用性。</li>\n<li style=\"text-align: justify; line-height: 2;\">可扩展性：GPT-3可以通过训练来适应不同的应用场景，从而扩展其功能。</li>\n<li style=\"text-align: justify; line-height: 2;\">可靠性：GPT-3训练数据的质量和数量都比以往模型更高，因此其生成的文本质量也更高，具有更高的可靠性。</li>\n<li>\n<p style=\"text-align: justify; line-height: 2;\">常见的应用场景包括：</p>\n<p style=\"text-align: justify; line-height: 2;\">1.聊天机器人：GPT-3可以作为客服助理，回答用户的查询和请求，并与用户进行对话。</p>\n<p style=\"text-align: justify; line-height: 2;\">2.内容生成：GPT-3可以生成文字、图像和音频内容，如新闻报道、广告、博客帖子和诗歌。</p>\n<p style=\"text-align: justify; line-height: 2;\">3.搜索引擎优化（SEO）：GPT-3可以帮助网站管理者生成有价值的关键字和描述，以提高搜索引擎排名。</p>\n<p style=\"text-align: justify; line-height: 2;\">4.语言翻译：GPT-3可以实现机器翻译，将文字从一种语言翻译成另一种语言。</p>\n<p style=\"text-align: justify; line-height: 2;\">5.问题回答：GPT-3可以根据用户提出的问题，在已有的知识库中查找答案，并回答用户的问题。</p>\n<p style=\"text-align: justify; line-height: 2;\">这些只是GPT-3的一些应用场景，随着技术的不断发展，GPT-3的应用场景还会有所增加。</p>\n<p style=\"text-align: justify; line-height: 2;\">GPT-3，作为一个领先的大规模语言生成模型，具有广泛的应用前景。由于GPT-3具有高效的文本生成能力，它可以在许多领域中使用，如聊天机器人、语音识别、文本翻译等。同时，随着人工智能技术的不断提高，GPT-3的应用领域还将不断扩大，以适应更多的实际需求。因此，GPT-3的未来前景非常广阔。</p>\n</li>\n</ol>', 0);
INSERT INTO `gpt_agreement` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `title`, `type`, `status`, `content`, `deleted`) VALUES (4, 'System', '2023-05-04 03:09:28', 'admin', '2024-03-14 11:29:43', '如何更好使用本产品？', 3, 1, '<p class=\"ql-align-justify\">首先需要转变一个认知，ChatMaster不同于传统的搜索引擎，它更像是你的一个私人助理，可以帮你解决很多文本创意相关的问题，也可以帮你检索收集知识，对文本的理解、归纳、整理、发散才是他的优势，简而言之，<strong><em>搜索引擎只能检索存在的信息，ChatMaster可以为你创造信息。</em></strong></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">&nbsp;</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">你如何使用他，他就有多大的威力，本产品的使用方式主要以问答形式进行，<strong>我们提的问题需要做到以下几点：</strong></p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">1.清晰明确:要让ChatMaster明确你的问题或要求，确保你的问题表达清晰，简短明了。</p><p class=\"ql-align-justify\">2.具体细节:尽可能提供更多具体的信息，例如关键词、例子或特定场景，以帮助更好地理解你的问题或要求。</p><p class=\"ql-align-justify\">3.相关背景:如果你的问题或要求需要一些背景信息，提供一些相关背景，这将有助于系统更好地理解问题，并提供更有针对性的回答。</p><p class=\"ql-align-justify\">4.合理的期望: 你的问题应该合理，并符合产品的能力和限制。</p><p class=\"ql-align-justify\">例如，一个好的问题可能是:“有一张excel表格，需要将T列的值全部取出，并以“,”分隔，请给出实际的例子。\"这个问题是清晰明确的，提供了具体的要求和背暑信息，同时也是合理的期望，因为ChatMaster可以提供相关的信息和例子来回答这个问题。</p><p class=\"ql-align-justify\">&nbsp;</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">四个关键词：主题，细节，背景，期望</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">又例如：“我在处理公司税务时遇到了一些困难，希望能够获得关于如何避免公司税务风险的建议。我们是一家小型企业，我们需要了解如何在合规的情况下最大限度地降低税务负担。请提供建议和指导，包括如何选择最适合我们公司的税务方案，以及如何在税务申报和报告方面遵循最佳实践。此外，请提供一些税务规划的建议，以帮助我们最大化利润，并避免任何潜在的税务问题。”</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">此外，得到初版答案后，我们可以继续对话，使其根据我们的要求去<strong>完善细节</strong>。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">&nbsp;</p>', 0);
INSERT INTO `gpt_agreement` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `title`, `type`, `status`, `content`, `deleted`) VALUES (5, 'System', '2023-05-04 03:09:28', 'admin', '2023-05-04 11:23:33', '如何提高GPT回答的逻辑性', 3, 0, '<p><br></p><p><br></p><p>本文适合对 ChatGPT 有复杂需求或想深入理解的用户阅读。简单的问题较少要用到本文提到的方法，而复杂问题，目前ChatGPT也不是最合适的工具。</p><p><br></p><p><br></p><p><br></p><blockquote><br></blockquote><blockquote>人脑的系统1 在熟悉情境中采取的模式是精确的，所作出的短期预测是准确的，遇到挑战时做出的第一反应也是迅速且基本恰当的。然而，系统1存在成见，在很多特定的情况下，这一系统易犯系统性错误。你会发现这个系统有时候会将原本较难的问题作简单化处理，对于逻辑学和统计学问题，它几乎一无所知。</blockquote><blockquote><br></blockquote><blockquote><br></blockquote><blockquote>——《思考，快与慢》</blockquote><blockquote><br></blockquote><h1>目录</h1><ol><li><br></li><li><br></li><li>为何没有逻辑</li><li><br></li><li><br></li><li><br></li><li>增强逻辑的方法</li><li><br></li><li><br></li><li><br></li><li><br></li><li>思维链(Chain of Thought)</li><li class=\"ql-indent-1\"><br></li><li><br></li><li><br></li><li>提出子问题（Self-Ask）</li><li class=\"ql-indent-1\"><br></li><li><br></li><li><br></li><li>给出多种方案</li><li class=\"ql-indent-1\"><br></li><li><br></li><li><br></li><li><br></li><li>向人类发起挑战</li><li><br></li><li><br></li><li><br></li><li><br></li><li>解决数理问题</li><li class=\"ql-indent-1\"><br></li><li><br></li><li><br></li><li>机器的直觉</li><li class=\"ql-indent-1\"><br></li><li><br></li><li><br></li></ol><h1>1 为何没有逻辑</h1><p>众所周知，ChatGPT 回答的<strong>逻辑性不好</strong>。</p><p><br></p><p><img src=\"http://127.0.0.1:8998/uploads/admin/202303/38714661e4f96f0f2b57eb21e6752842.png\" alt=\"图片\"></p><p>正确答案是：7个偶数，3个奇数。ChatGPT甚至连数字都抄错了</p><p><br></p><p>然而，我们人类很多时候也是这样，当我们依赖于直觉判断，或是思维跳得太快，没有按部就班一步一步推理时，就很容易犯错，得出错误的答案。不过，当我们遇到一些问题时（如257*37=？），我们懂得不要着急给出答案（就算想也做不到啊），而是会在脑海里先计算推理一番，用「工作记忆」记住计算过程中的临时结果，对于更难的问题，我们会先做一下草稿或利用工具，由此来得到正确的答案。</p><p><br></p><p><em>注：人脑的「工作记忆」是一种记忆容量有限的认知系统，被用以暂时保存信息。工作记忆对于推理以及指导决策和行为有重要影响。</em></p><p><br></p><p><strong>当我们让 ChatGPT 回答问题时，我们并没有给它思考的空间</strong>，它无法利用「工作记忆」，也无法用草稿纸，需要直接给出答案（可能是因为用于训练的数据，少有推理过程）。但如果我们可以让它像人类一样，<strong>把需要多步推理的问题，拆分成子问题</strong>，便可以帮助ChatGPT提高回答的逻辑性，这一点被证明是有效的。</p><p><br></p><p>我们可以通过改写 prompt 来达到这个目的，以下将介绍几种提高逻辑性的方法。</p><p><br></p><p><br></p><p><br></p><p>1.方法有很多，但并不需要一一记住，<strong>本质都是引导ChatGPT给出中间的推理步骤</strong>，不要直接给结果。</p><p><br></p><p><br></p><p>2.随着ChatGPT本身逻辑性的提高，在某些问题上，可以直接得到很好的答案，而不需要使用额外的方法。</p><p><br></p><p><br></p><p>3.这些方法源自无对话功能的GPT-3，而ChatGPT可以来回对话，在对话中修正答案，某些时候无需用到这些方法。</p><p><br></p><p><br></p><p><br></p><h1>2 增强逻辑的方法</h1><h2>2.1 思维链(Chain of Thought)</h2><p><strong>CoT可是说是最重要的一种方法</strong>，因此有时候也把其它方法统称为CoT。这个方法利用GPT-3是一个<strong>文本预测器</strong>的特性，它需要尽量保持上下文的连贯性。</p><p><br></p><p>CoT 主要分为两种：</p><p><br></p><p><br></p><ol><li><br></li><li><br></li><li><strong>Zero-shot-CoT</strong>：在 prompt 的后面加上 Let\'s think step by step（一步一步地思考）</li><li><br></li><li><br></li><li><br></li><li><strong>Chain of Thought</strong>：展示一个相似问题的推理过程，告诉ChatGPT应该这么来（推荐使用）。</li><li><br></li><li><br></li></ol><p><em>区别是前者没有给出推理的示例（zero-shot表示0示例），后者给出至少一个示例。</em></p><p><br></p><p>我们用这 2 种方法，分别测试一下前面数奇数偶数的问题。</p><p><br></p><p><br></p><h3>Zero-shot-CoT</h3><p>在问题的最后加上 Let\'s think step by step<img src=\"http://127.0.0.1:8998/uploads/admin/202303/77f0f456438b054feceba121b8a18477.png\" alt=\"图片\"></p><p><br></p><p>好吧，虽然列出的过程，可最后一步不知为什么失败了。</p><p><br></p><p>当然在这个问题中失败了并不意味着以后不能用了，GPT-3并不是一个确定的系统，在某些情况下也许可以得到正确的答案。但这个方法不稳定。</p><p><br></p><p>Zero-shot-CoT最大的用处或许是可以为我们的第二种方法 CoT 提供例子。</p><p><br></p><p><br></p><h3>Chain of Thought</h3><p>将上一个方法的结果，改写一下，在每一步当中加入当前步数的累计个数，然后给ChatGPT作为处理此类问题的示例。</p><p><br></p><p>现在我们得到了正确的答案。<img src=\"http://127.0.0.1:8998/uploads/admin/202303/3d5e55e16ed2cd40a9d1f77f7c15fb5e.png\" alt=\"图片\">同样，尽管把这道题做对了，但改下数字或换个题目，可能就又不行了。</p><p><br></p><p>ChatGPT通常都很粗心，<strong>需要把推理的过程需要拆分得很细</strong>，否则很容易犯错，这样的错误可能只是抄错了数字，但一步错便步步错了。</p><p><br></p><p>这还只是很简单的问题，便需要写这么复杂的prompt才能解决，甚至还不能保证结果是对的，可见<strong>对待此类问题，我们应当另寻出路</strong>，除非是为了运用费曼学习法，将知识讲于它人听，以加深对知识的理解。</p><p><br></p><p>对这个特定的问题：实现一个自动数「奇偶个数」的功能。对大部分人来说，写prompt还是比写代码简单的，但就算如此，ChatGPT的效率是低下的，代码运算只要几毫秒就能得到结果，而ChatGPT要一个字一个字蹦出来，至少要花个十几秒。所以，在ChatGPT能够聪明高效地解决问题前，对于传统的计算问题，还是应该使用传统的方式。</p><p><br></p><p>这并不是说 ChatGPT 或 Chain of Thought 派不上用场。<strong>对于需要对语言有所理解才能处理的问题，传统的编程方式难以使用，这是ChatGPT施展手脚的地方。</strong>人类有大部分知识都是用语言记录，有很大的应用空间。</p><p><br></p><p>接下来的 2 种方法，是CoT的变种。</p><p><br></p><p><br></p><h2>2.2 提出子问题（Self-Ask）</h2><p>有些时候，中间的推理过程更为复杂一些，需要将问题分解成子问题。</p><p><br></p><p>Self-Ask 是一种让 GPT-3 根据问题自动提出子问题的方法，要求GPT-3判断一个问题是否提出子问题，先解决子问题后，再给出最后的答案。</p><p><br></p><p><img src=\"http://127.0.0.1:8998/uploads/admin/202303/bb23a533405f360906b931a7b7d0493b.png\" alt=\"图片\"><em>我们总是可以用很简单的问题（小学算术）作为示例，然后让ChatGPT处理更难的问题</em><img src=\"http://127.0.0.1:8998/uploads/admin/202303/b84c624dab37f743d17e0981676d3fd5.png\" alt=\"图片\"></p><p><br></p><p><br></p><h2>2.3 给出多种方案（Self&nbsp;Consistency)</h2><p>要求GPT-3给出一个问题的多个解决方案，最后综合考虑几个方案，得出最终的答案。<img src=\"http://127.0.0.1:8998/uploads/admin/202303/8322afbe5249febb91c16e02834f7ac9.png\" alt=\"图片\"></p><p><br></p><p>对于搜索的计算问题，传统的编程有2种算法：深度搜索、广度搜索。如果我们将ChatGPT处理的问题类比到传统的计算问题上，那么Self Consistency可以类比成是广度搜索的组成部分，而Self Ask则是深度搜索的组成部分。</p><p><br></p><p><img src=\"http://127.0.0.1:8998/uploads/admin/202303/a5e3960307e8086e6d438b9bae8e6989.png\" alt=\"图片\"></p><p>该方法已被证明可以提高算术、常识和符号推理任务的结果。即使发现常规 CoT 无效，改方法仍然能够改善结果</p><p><br></p><p>除了以上方法外，GPT-3 还能够结合其他工具，例如将问题转成代码，或是从外部获取信息，以此来提高GPT-3回答的准确性，在此不做详细介绍。</p><p><br></p><p><br></p><h1>3 向人类发起挑战</h1><h2>3.1 解决数理问题</h2><p>Google利用上面提到的这些方法，做了一个叫Minerva的系统，提高了大语言模型在数理逻辑方面的表现。https://ai.googleblog.com/2022/06/minerva-solving-quantitative-reasoning.html</p><p><br></p><p><br></p><blockquote><br></blockquote><blockquote>Minerva 结合了最新的 prompt 和评估技术，以更好地解决数学问题。这些包括 Chain of Thought 或 scratchpad （在提出新问题之前，Minerva 会使用几种step-by-step的方法解决现有问题），以及多数投票（也就是Self Consistency）。</blockquote><blockquote><br></blockquote><p><em>注：scratchpad会将过程中的结果记录下来，和上面数奇偶的方法相似，也可以认为是一种CoT。</em></p><p><br></p><p><img src=\"http://127.0.0.1:8998/uploads/admin/202303/6fb320ef754e472ac5e628edcb9a807a.png\" alt=\"图片\"></p><p>Minerva在数理上的表现</p><p><br></p><p>当然，正如我们前面提到的，GPT-3在不借助其它工具时，对需要多步推理的问题，处理效率不高，因此解决数理问题，更好的方法可能是和<strong>Wolfram</strong>结合，取GPT-3理解语言之长，补其逻辑推理之短。</p><p><br></p><p><br></p><h2>3.2 机器的直觉</h2><p>最后我们来比较一下人类和ChatGPT谁的「直觉」比较厉害。</p><p><br></p><p><br></p><blockquote><br></blockquote><blockquote>下面是一个相对简单的题目，别费力去分析它，凭直觉做做看：</blockquote><blockquote><br></blockquote><blockquote><br></blockquote><blockquote><strong>「球拍」和「球」共花 1.10 美元，「球拍」比「球」贵 1 美元，问「球」多少钱？</strong></blockquote><blockquote><br></blockquote><blockquote><br></blockquote><blockquote><strong>&nbsp;</strong></blockquote><blockquote><br></blockquote><blockquote><br></blockquote><blockquote><em>你会马上想到一个数字，这个数字当然就是10，即10美分。这道简单的难题之所以与众不同，是因为它能引出一个直觉性的、吸引人的但却错误的答案。计算一下，你就会发现。如果球花费10美分的话，总共就要花1.20美元（球10美分，球拍1.10美元），而不是1.10美元。正确答案是5美分。</em></blockquote><blockquote><br></blockquote><blockquote><br></blockquote><blockquote>——《思考，快与慢》</blockquote><blockquote><br></blockquote><p><img src=\"http://127.0.0.1:8998/uploads/admin/202303/ab97edbf5f6b311709fca40eea9b3df2.png\" alt=\"图片\"><em>好吧，这已经不是「直觉」了，ChatGPT开始使用「系统2」了</em></p>', 0);
COMMIT;

-- ----------------------------
-- Table structure for gpt_assistant
-- ----------------------------
DROP TABLE IF EXISTS `gpt_assistant`;
CREATE TABLE `gpt_assistant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `avatar` varchar(255) NOT NULL DEFAULT '' COMMENT '头像',
  `icon` varchar(250) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '角色图标',
  `title` varchar(250) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '角色名称',
  `tag` varchar(250) NOT NULL DEFAULT '' COMMENT '标签',
  `main_model` smallint(6) DEFAULT '0' COMMENT '主模型',
  `description` varchar(512) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '角色描述',
  `first_message` varchar(512) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT 'AI打招呼',
  `system_prompt` varchar(512) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '系统提示词',
  `type_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '助手分类id',
  `sort` int(11) NOT NULL DEFAULT '1' COMMENT '排序',
  `status` smallint(6) NOT NULL DEFAULT '1' COMMENT '状态 0 禁用 1 启用',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`),
  KEY `type_id` (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='AI助理功能';

-- ----------------------------
-- Records of gpt_assistant
-- ----------------------------
BEGIN;
INSERT INTO `gpt_assistant` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `avatar`, `icon`, `title`, `tag`, `main_model`, `description`, `first_message`, `system_prompt`, `type_id`, `sort`, `status`, `deleted`) VALUES (1, 'System', '2023-12-08 06:58:00', 'System', '2024-03-12 20:33:42', '', '🍃', '赏桂作诗', '赏桂作诗', 0, '到了桂花盛开的季节，请以桂花为灵感，创作一首表达其独特香气的诗', '', '到了桂花盛开的季节，你是一位富有诗意的年轻诗人，请以桂花为灵感，创作一首表达其独特香气和秋天的关联的诗。\n字数：[五言或七言]\n内容：[体现桂花的馥郁香气，以及它在秋天的特殊意义]\n表达风格：[清新自然、对仗工整、含义隽永]', 1, 1, 1, 0);
INSERT INTO `gpt_assistant` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `avatar`, `icon`, `title`, `tag`, `main_model`, `description`, `first_message`, `system_prompt`, `type_id`, `sort`, `status`, `deleted`) VALUES (2, 'System', '2023-12-08 06:58:00', 'System', '2024-03-12 20:33:42', '', '📖', 'AI小说工坊', 'AI小说工坊', 0, '想象无界，挖掘人工智能未来的神奇故事，下一个星云奖', '', '你是一位才华横溢的科幻小说作家，请为我创作一部以人工智能大战哥斯拉为主题的小说第一章\n小说的开篇：[在未来的世界，人工智能已经发展到了顶峰，而此时，传说中的巨兽哥斯拉突然出现，威胁着世界的安宁。人工智能作为人类的盾牌，将如何迎接这个挑战？]\n要求：[故事富有想象力和吸引力，大量讲述细节，有小人物的悲欢离合，为读者留下悬念]', 1, 1, 1, 0);
INSERT INTO `gpt_assistant` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `avatar`, `icon`, `title`, `tag`, `main_model`, `description`, `first_message`, `system_prompt`, `type_id`, `sort`, `status`, `deleted`) VALUES (3, 'System', '2023-12-08 07:02:15', 'System', '2024-03-12 20:33:42', '', '💼', '打工人解压', '打工人解压', 0, '别再为工作焦虑啦，快来找到你的解压秘籍，提升工作幸福感', '', '你有丰富的解压经验帮助打工人解决压力问题，请根据以下要求为客户提供专业建议：\n解压方法：[适合办公室快节奏工作精神压力大的打工人]\n实施步骤：[详细描述缓解身体和精神疲劳的方法]\n效果预期：[减轻压力，让人恢复活力]\n要求：提供易于理解和实施的指导，确保建议可实施性强，适合在工作间隙进行', 3, 1, 1, 0);
INSERT INTO `gpt_assistant` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `avatar`, `icon`, `title`, `tag`, `main_model`, `description`, `first_message`, `system_prompt`, `type_id`, `sort`, `status`, `deleted`) VALUES (4, 'System', '2023-12-08 07:08:18', 'System', '2024-03-12 20:33:42', '', '👀', '爽文创作', '爽文创作', 0, '一觉醒来，所有人的数学水平下降一万倍……', '', '你是一位杰出的小说家，专长于编织引人入胜的故事。\n请根据以下要求创作一篇短篇爽文小说：\n故事主题：[一夜之间所有人数学水平下降一万倍]\n故事背景：[你正在为高数烦恼，突然一夜之间所有人数学水平下降一万倍，最厉害的数学家只会二元一次方程，而你已经掌握积分公式]\n情节设置：[详细描写你在正讲着加减法的大学课堂上展现惊人数学能力的种种场景，从同学们对主角的惊叹中介绍故事背景]\n要求：[故事应富有想象力，情节紧凑，细节描写丰富，代入感强，让读者感到快乐]', 8, 1, 1, 0);
COMMIT;

-- ----------------------------
-- Table structure for gpt_assistant_type
-- ----------------------------
DROP TABLE IF EXISTS `gpt_assistant_type`;
CREATE TABLE `gpt_assistant_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '分类名称',
  `icon` varchar(128) NOT NULL DEFAULT '' COMMENT 'icon图标',
  `sort` int(1) NOT NULL DEFAULT '0' COMMENT '排序',
  `status` smallint(6) DEFAULT '1' COMMENT '状态 0 禁用 1 启用',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='助手分类';

-- ----------------------------
-- Records of gpt_assistant_type
-- ----------------------------
BEGIN;
INSERT INTO `gpt_assistant_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `sort`, `status`, `deleted`) VALUES (1, 'admin', '2023-11-22 11:15:20', 'System', '2024-03-12 20:43:38', '创意写作', 'icon-park-outline:picture', 97, 1, 0);
INSERT INTO `gpt_assistant_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `sort`, `status`, `deleted`) VALUES (2, 'admin', '2023-11-22 11:15:29', 'System', '2023-12-08 06:59:13', '学生', 'icon-park-outline:picture', 0, 1, 0);
INSERT INTO `gpt_assistant_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `sort`, `status`, `deleted`) VALUES (3, 'admin', '2023-11-22 11:15:36', 'System', '2023-12-08 07:01:36', '职场人', 'icon-park-outline:picture', 0, 1, 0);
INSERT INTO `gpt_assistant_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `sort`, `status`, `deleted`) VALUES (4, 'admin', '2023-11-22 11:15:45', 'System', '2023-12-08 07:03:08', '家长', 'icon-park-outline:picture', 0, 1, 0);
INSERT INTO `gpt_assistant_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `sort`, `status`, `deleted`) VALUES (5, 'admin', '2023-11-22 11:15:51', 'System', '2024-03-12 20:43:57', '媒体人', 'icon-park-outline:picture', 95, 1, 0);
INSERT INTO `gpt_assistant_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `sort`, `status`, `deleted`) VALUES (6, 'admin', '2023-11-22 11:16:00', 'System', '2023-12-08 07:05:28', '老师', 'icon-park-outline:picture', 0, 1, 0);
INSERT INTO `gpt_assistant_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `sort`, `status`, `deleted`) VALUES (7, 'admin', '2023-11-22 11:16:13', 'System', '2024-03-12 20:43:52', '程序员', 'icon-park-outline:picture', 96, 1, 0);
INSERT INTO `gpt_assistant_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `sort`, `status`, `deleted`) VALUES (8, 'admin', '2023-11-22 11:16:24', 'System', '2024-03-12 20:43:34', '摸鱼大师', 'icon-park-outline:picture', 98, 1, 0);
INSERT INTO `gpt_assistant_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `sort`, `status`, `deleted`) VALUES (9, 'admin', '2023-11-22 11:16:51', 'admin', '2024-03-12 20:43:28', '生活家', 'icon-park-outline:picture', 99, 1, 0);
INSERT INTO `gpt_assistant_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `sort`, `status`, `deleted`) VALUES (10, 'System', '2023-12-08 07:10:00', 'System', '2023-12-08 07:10:04', '营销人', 'icon-park-outline:picture', 0, 1, 0);
INSERT INTO `gpt_assistant_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `sort`, `status`, `deleted`) VALUES (11, 'System', '2023-12-08 07:11:30', 'System', '2023-12-08 07:11:34', 'AI作画', 'icon-park-outline:picture', 0, 1, 0);
COMMIT;

-- ----------------------------
-- Table structure for gpt_chat
-- ----------------------------
DROP TABLE IF EXISTS `gpt_chat`;
CREATE TABLE `gpt_chat` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `chat_number` varchar(32) NOT NULL DEFAULT '' COMMENT '聊天编号',
  `assistant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色id',
  `uid` varchar(32) NOT NULL DEFAULT '' COMMENT '游客id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `title` varchar(250) DEFAULT '' COMMENT '聊天摘要',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='聊天摘要';

-- ----------------------------
-- Table structure for gpt_chat_message
-- ----------------------------
DROP TABLE IF EXISTS `gpt_chat_message`;
CREATE TABLE `gpt_chat_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `chat_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'chat_id',
  `message_id` varchar(64) NOT NULL DEFAULT '' COMMENT '消息id',
  `parent_message_id` varchar(128) NOT NULL DEFAULT '' COMMENT '回复消息id',
  `model` varchar(64) NOT NULL DEFAULT '' COMMENT '模型',
  `model_version` varchar(64) NOT NULL DEFAULT '' COMMENT '模型版本',
  `content` longtext CHARACTER SET utf8mb4 COMMENT '消息内容',
  `content_type` varchar(32) NOT NULL DEFAULT '' COMMENT '内容类型：text：文字 image : 图片',
  `role` varchar(250) NOT NULL DEFAULT '' COMMENT '角色',
  `finish_reason` varchar(250) DEFAULT '' COMMENT '结束原因',
  `status` smallint(6) DEFAULT '1' COMMENT '状态 1 回复中 2正常 3 失败',
  `app_key` varchar(128) NOT NULL DEFAULT '' COMMENT '使用的key',
  `used_tokens` bigint(20) NOT NULL DEFAULT '0' COMMENT '使用token',
  `response` longtext COMMENT '响应全文',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='对话消息';

-- ----------------------------
-- Table structure for gpt_comb
-- ----------------------------
DROP TABLE IF EXISTS `gpt_comb`;
CREATE TABLE `gpt_comb` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `title` varchar(250) NOT NULL DEFAULT '' COMMENT '套餐名称',
  `type` smallint(6) DEFAULT '1' COMMENT '套餐类型 1 次数 2 天数',
  `num` int(11) DEFAULT '0' COMMENT '包含次数',
  `origin_price` decimal(10,2) DEFAULT '0.00' COMMENT '原价',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '价格',
  `status` smallint(6) DEFAULT '1' COMMENT '状态 0 禁用 1 启用',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='会员套餐';

-- ----------------------------
-- Records of gpt_comb
-- ----------------------------
BEGIN;
INSERT INTO `gpt_comb` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `title`, `type`, `num`, `origin_price`, `price`, `status`, `deleted`) VALUES (1, 'System', '2023-05-04 11:37:10', 'System', '2023-12-27 03:06:08', '体验套餐', 1, 20, 2.00, 0.01, 1, 0);
INSERT INTO `gpt_comb` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `title`, `type`, `num`, `origin_price`, `price`, `status`, `deleted`) VALUES (2, 'System', '2023-05-04 11:52:08', 'System', '2023-12-27 03:06:09', '20次包', 1, 20, 10.00, 5.00, 1, 0);
INSERT INTO `gpt_comb` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `title`, `type`, `num`, `origin_price`, `price`, `status`, `deleted`) VALUES (3, 'System', '2023-05-04 11:52:36', 'System', '2023-12-27 03:06:10', '100次30天包', 1, 100, 50.00, 20.00, 1, 0);
INSERT INTO `gpt_comb` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `title`, `type`, `num`, `origin_price`, `price`, `status`, `deleted`) VALUES (4, 'System', '2023-05-04 11:53:08', 'System', '2023-12-27 03:06:12', '200次季度包', 1, 200, 100.00, 39.90, 1, 0);
INSERT INTO `gpt_comb` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `title`, `type`, `num`, `origin_price`, `price`, `status`, `deleted`) VALUES (5, 'System', '2023-05-04 11:53:36', 'System', '2023-12-27 03:06:14', '500次全年包', 1, 5000, 199.99, 99.99, 1, 0);
COMMIT;

-- ----------------------------
-- Table structure for gpt_model
-- ----------------------------
DROP TABLE IF EXISTS `gpt_model`;
CREATE TABLE `gpt_model` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '模型名称',
  `icon` varchar(255) NOT NULL DEFAULT '' COMMENT '模型logo',
  `model` varchar(32) NOT NULL DEFAULT '' COMMENT '模型名称',
  `version` varchar(64) NOT NULL DEFAULT '' COMMENT '模型版本',
  `status` smallint(6) NOT NULL DEFAULT '1' COMMENT '状态 0 禁用 1 启用',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='大模型信息';

-- ----------------------------
-- Records of gpt_model
-- ----------------------------
BEGIN;
INSERT INTO `gpt_model` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `model`, `version`, `status`, `deleted`) VALUES (1, 'System', '2023-12-01 01:23:51', 'System', '2024-05-12 17:34:18', 'ChatGPT', '', 'CHAT_GPT', 'gpt-3.5-turbo-0613', 0, 0);
INSERT INTO `gpt_model` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `model`, `version`, `status`, `deleted`) VALUES (2, 'System', '2023-12-01 01:24:43', 'System', '2024-05-12 17:34:18', '文心一言', '', 'WENXIN', 'ERNIE_Bot_turbo', 0, 0);
INSERT INTO `gpt_model` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `model`, `version`, `status`, `deleted`) VALUES (3, 'System', '2023-12-01 01:25:18', 'System', '2024-05-12 17:34:18', '通义千问', '', 'QIANWEN', 'qwen-turbo', 0, 0);
INSERT INTO `gpt_model` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `model`, `version`, `status`, `deleted`) VALUES (4, 'System', '2023-12-01 01:25:29', 'System', '2024-05-12 17:34:32', '讯飞星火', '', 'SPARK', 'v3.5', 1, 0);
INSERT INTO `gpt_model` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `model`, `version`, `status`, `deleted`) VALUES (5, 'System', '2023-12-27 00:38:20', 'admin', '2024-05-12 17:34:19', '智谱清言', '', 'ZHIPU', 'chatGLM_6b_SSE', 0, 0);
COMMIT;

-- ----------------------------
-- Table structure for gpt_openkey
-- ----------------------------
DROP TABLE IF EXISTS `gpt_openkey`;
CREATE TABLE `gpt_openkey` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `app_id` varchar(64) NOT NULL DEFAULT '' COMMENT 'appid',
  `app_key` varchar(64) NOT NULL DEFAULT '' COMMENT 'app key对应openai的token',
  `app_secret` varchar(128) NOT NULL DEFAULT '' COMMENT 'app密钥',
  `total_tokens` bigint(20) NOT NULL DEFAULT '0' COMMENT '总额度',
  `used_tokens` bigint(20) NOT NULL DEFAULT '0' COMMENT '已用额度',
  `surplus_tokens` bigint(20) NOT NULL DEFAULT '0' COMMENT '剩余token',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态 0 禁用 1 启用',
  `model` varchar(32) NOT NULL DEFAULT '' COMMENT '模型',
  `remark` varchar(250) DEFAULT '' COMMENT '备注',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`),
  KEY `app_key` (`app_key`),
  KEY `model` (`model`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='openai token';

-- ----------------------------
-- Records of gpt_openkey
-- ----------------------------
BEGIN;
INSERT INTO `gpt_openkey` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `app_id`, `app_key`, `app_secret`, `total_tokens`, `used_tokens`, `surplus_tokens`, `status`, `model`, `remark`, `deleted`) VALUES (1, 'admin', '2023-05-04 10:10:05', 'System', '2024-05-12 17:30:02', '', '', '', 100000, 0, 0, 0, 'CHAT_GPT', 'master@gmail.com', 0);
INSERT INTO `gpt_openkey` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `app_id`, `app_key`, `app_secret`, `total_tokens`, `used_tokens`, `surplus_tokens`, `status`, `model`, `remark`, `deleted`) VALUES (2, 'System', '2023-09-07 09:20:48', 'System', '2024-05-12 17:30:02', '', '', '', 100000, 0, 0, 0, 'WENXIN', '', 0);
INSERT INTO `gpt_openkey` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `app_id`, `app_key`, `app_secret`, `total_tokens`, `used_tokens`, `surplus_tokens`, `status`, `model`, `remark`, `deleted`) VALUES (3, 'System', '2023-09-12 02:24:22', 'System', '2024-05-12 17:30:03', '', '', '', 100000, 0, 0, 0, 'QIANWEN', '', 0);
INSERT INTO `gpt_openkey` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `app_id`, `app_key`, `app_secret`, `total_tokens`, `used_tokens`, `surplus_tokens`, `status`, `model`, `remark`, `deleted`) VALUES (4, 'System', '2023-09-12 02:25:27', 'master', '2024-05-12 17:36:06', '', '', '', 100000, 0, 0, 1, 'SPARK', '', 0);
INSERT INTO `gpt_openkey` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `app_id`, `app_key`, `app_secret`, `total_tokens`, `used_tokens`, `surplus_tokens`, `status`, `model`, `remark`, `deleted`) VALUES (5, 'System', '2023-12-27 00:37:59', 'System', '2024-05-12 17:30:03', '', '', '', 100000, 0, 0, 0, 'ZHIPU', '', 0);
COMMIT;

-- ----------------------------
-- Table structure for gpt_order
-- ----------------------------
DROP TABLE IF EXISTS `gpt_order`;
CREATE TABLE `gpt_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `success_time` datetime DEFAULT NULL COMMENT '支付成功时间',
  `trade_no` varchar(250) NOT NULL DEFAULT '' COMMENT '订单号',
  `transaction_id` varchar(250) DEFAULT '' COMMENT '渠道交易ID',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '下单用户',
  `comb_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '购买套餐',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '价格',
  `chanel` smallint(6) DEFAULT '-1' COMMENT '支付渠道 1 微信小程序 2、微信公众号 3、微信h5 4、微信扫码',
  `status` smallint(6) DEFAULT '-1' COMMENT '订单状态 1 待支付 2 支付成功 3 支付超时 4 已退款',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单';

-- ----------------------------
-- Records of gpt_order
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gpt_redemption
-- ----------------------------
DROP TABLE IF EXISTS `gpt_redemption`;
CREATE TABLE `gpt_redemption` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `code` varchar(250) DEFAULT '' COMMENT '兑换码',
  `num` int(11) DEFAULT '0' COMMENT '可兑次数',
  `user_id` bigint(20) DEFAULT '0' COMMENT '兑换人',
  `recieve_time` int(10) DEFAULT NULL COMMENT '兑换时间',
  `status` int(1) DEFAULT '0' COMMENT '状态 0 未兑换 1 已兑换',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='兑换码';

-- ----------------------------
-- Records of gpt_redemption
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gpt_upload_config
-- ----------------------------
DROP TABLE IF EXISTS `gpt_upload_config`;
CREATE TABLE `gpt_upload_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `title` varchar(250) DEFAULT NULL COMMENT '配置名称',
  `upload_replace` tinyint(4) DEFAULT '0' COMMENT '覆盖同名文件',
  `thumb_status` tinyint(4) DEFAULT '0' COMMENT '缩图开关',
  `thumb_width` varchar(250) DEFAULT '' COMMENT '缩放宽度',
  `thumb_height` varchar(250) DEFAULT '' COMMENT '缩放高度',
  `thumb_type` smallint(6) DEFAULT '0' COMMENT '缩图方式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='缩略图配置';

-- ----------------------------
-- Records of gpt_upload_config
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gpt_upload_file
-- ----------------------------
DROP TABLE IF EXISTS `gpt_upload_file`;
CREATE TABLE `gpt_upload_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `filepath` varchar(255) DEFAULT '' COMMENT '图片路径',
  `hash` varchar(32) DEFAULT '' COMMENT '文件hash值',
  `disk` varchar(20) DEFAULT '' COMMENT '存储方式',
  `type` tinyint(10) DEFAULT NULL COMMENT '文件类型',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `hash` (`hash`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='文件管理';

-- ----------------------------
-- Records of gpt_upload_file
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gpt_user
-- ----------------------------
DROP TABLE IF EXISTS `gpt_user`;
CREATE TABLE `gpt_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  `uid` varchar(255) NOT NULL DEFAULT '' COMMENT 'Uuid\n',
  `name` varchar(250) NOT NULL DEFAULT '' COMMENT '姓名',
  `nick_name` varchar(250) NOT NULL DEFAULT '' COMMENT '昵称',
  `tel` varchar(250) NOT NULL DEFAULT '' COMMENT '手机号',
  `password` varchar(250) NOT NULL DEFAULT '' COMMENT '密码',
  `avatar` varchar(250) NOT NULL DEFAULT '' COMMENT '头像',
  `openid` varchar(250) NOT NULL DEFAULT '' COMMENT 'openid',
  `unionid` varchar(250) NOT NULL DEFAULT '' COMMENT 'unionid',
  `ip` varchar(250) NOT NULL DEFAULT '' COMMENT '登录ip',
  `context` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否开启上下文',
  `num` int(11) DEFAULT '0' COMMENT '调用次数',
  `share_id` bigint(20) DEFAULT '0' COMMENT '邀请人',
  `type` int(1) DEFAULT '1' COMMENT '用户类型 1 微信小程序 2 公众号 3 手机号',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态 0 禁用 1 启用\n',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='会员用户';

-- ----------------------------
-- Records of gpt_user
-- ----------------------------
BEGIN;
INSERT INTO `gpt_user` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `login_time`, `uid`, `name`, `nick_name`, `tel`, `password`, `avatar`, `openid`, `unionid`, `ip`, `context`, `num`, `share_id`, `type`, `status`, `deleted`) VALUES (1, 'System', '2024-05-12 17:35:36', 'System', '2024-05-12 17:37:39', '2024-05-12 17:35:36', 'fb161d02-87ec-4651-8237-c6f0b6dff41c', '用户7JOwBoz2', '用户7JOwBoz2', '13866668888', '$2a$10$UYXDZq6AJo60Att/6E3dh.rLBdGgbUdV7IDILimFmXWzRKn6GgOJ.', '', '', '', '', 0, 2, 0, 3, 0, 0);
INSERT INTO `gpt_user` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `login_time`, `uid`, `name`, `nick_name`, `tel`, `password`, `avatar`, `openid`, `unionid`, `ip`, `context`, `num`, `share_id`, `type`, `status`, `deleted`) VALUES (2, 'System', '2024-05-13 14:58:45', 'System', '2024-05-13 14:58:45', '2024-05-13 14:58:45', '541a234d-71dd-424d-924d-5b07f3235b01', '测试', '测试', '13888888888', '$2a$10$TnbLDOiR9Wydzb50TIBwuuGRmQVRzc843pqU.a.KqEuwpogS7sdWO', '', '', '', '', 0, 0, 0, 3, 0, 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
