/*
 Navicat Premium Data Transfer

 Source Server         : ChatMaster
 Source Server Type    : MySQL
 Source Server Version : 50744 (5.7.44-log)
 Source Host           : 127.0.0.1:3306
 Source Schema         : chat_master

 Target Server Type    : MySQL
 Target Server Version : 50744 (5.7.44-log)
 File Encoding         : 65001

 Date: 10/02/2025 16:34:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

--
-- 数据库： `chat_master`
--
CREATE DATABASE IF NOT EXISTS `chat_master` DEFAULT CHARACTER SET utf8mb4;
USE `chat_master`;

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
INSERT INTO `gpt_agreement` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `title`, `type`, `status`, `content`, `deleted`) VALUES (1, 'System', '2023-05-04 03:09:28', 'admin', '2024-03-14 11:36:56', '用户协议', 1, 1, '<p class=\"ql-align-justify\">欢迎使用“ChatMaster”（以下简称“本产品”）。本协议是您（以下简称“用户”）与Master科技有限公司（以下简称“本公司”）之间关于使用本产品的协议。本协议规定用户使用本产品的条件和限制，包括但不限于用户的权利和义务以及本公司的责任和义务。用户在使用本产品之前，请仔细阅读本协议的全部内容，特别是加粗部分。用户选择使用本产品即表示用户已经阅读、理解并接受本协议的全部内容。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">一、服务内容</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">本产品是一款基于AI语言大模型技术，提供对话服务的产品，主要功能包括但不限于提供创作、游玩、学习、编程等方面的对话服务。用户可通过本产品获取有关创作、学习、编程等方面的信息，并根据自己的需要进行对话。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">二、用户权利和义务</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">1.用户可以在遵守本协议及相关法律法规的前提下使用本产品，并享有相应的咨询服务。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">2.用户不得利用本产品进行违法活动，不得利用本产品干扰或破坏本产品的正常运行。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">3.用户应遵守网络道德，不得在使用本产品时发布、传播违反国家法律法规和社会公德的信息。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">4.用户应妥善保管个人账号和密码，并承担因账号和密码泄露而导致的损失和后果。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">5.用户应当遵守本公司对于使用本产品的其他规定。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">三、本公司权利和义务</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">1.本公司有权对本产品的服务内容进行调整和变更，并及时通知用户。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">2.本公司有权暂停或终止本产品的服务，并无需事先通知用户。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">3.本公司有权根据相关法律法规和政策要求，在符合法律规定的前提下对用户的信息进行管理和使用。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">4.本公司有义务采取必要的技术措施保护用户信息的安全。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">四、免责声明</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">1.用户应当自行承担使用本产品所造成的风险和后果。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">2.本公司不保证本产品能够满足用户的全部需求，不保证本产品的使用是不中断、及时、安全、准确、完整和可靠的。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">3.本公司不对用户使用本产品而产生的任何直接、间接、附带的、特殊的、惩罚性或因使用本产品而引起的任何损失或损害负责，包括但不限于财产损失、利润损失、商业机会损失等。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">五、知识产权</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">1.本产品所包含的所有内容，包括但不限于文字、图片、音频、视频、软件、程序、代码、数据等，均属于本公司或相关权利人的知识产权。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">2.用户不得以任何形式将本产品的内容进行复制、转载、修改、传播或用于其他商业用途。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">3.未经本公司或相关权利人书面同意，用户不得以任何形式使用、修改、复制或传播本产品的商标、标识等知识产权。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">六、协议的变更和生效</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">1.本公司有权根据需要修改本协议，并在本产品上予以公布。用户应定期关注本协议的变更情况。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">2.本协议的任何修改均在本公司公布之日生效。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">3.如用户不同意本协议的修改内容，应立即停止使用本产品。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">七、争议解决</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">1.本协议的签订、效力、履行和争议解决均适用中华人民共和国法律法规。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">2.如发生本协议履行过程中的争议，应通过友好协商解决，协商不成的，任何一方有权向有管辖权的人民法院提起诉讼。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">八、其他</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">1.本协议构成用户和本公司之间的全部协议，并取代所有先前的书面或口头协议。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">2.如本协议中任何一条被认定为无效或不可执行，不影响其他条款的效力。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">3.本公司未能行使或执行本协议规定的任何权利或规定，不构成对该权利或规定的放弃。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">4.本协议中的标题仅为方便起见，不影响本协议的解释和执行。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">&nbsp;</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">本公司保留在法律允许范围内对本协议的最终解释权。用户在使用本产品时应遵守本协议及本公司的其他规定，以保证自身的合法权益。</p>', 0);
INSERT INTO `gpt_agreement` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `title`, `type`, `status`, `content`, `deleted`) VALUES (2, 'System', '2023-05-04 03:09:28', 'admin', '2024-03-14 11:33:22', '隐私政策', 2, 1, '<p class=\"ql-align-justify\">一、信息收集</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">为了为用户提供更好的服务，我们可能会收集用户的以下信息：</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">1.设备信息：我们可能会收集您使用ChatMaster时所用的设备类型、设备号、操作系统版本、设备设置、应用程序版本号以及设备标识符等信息。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">2.日志信息：当您使用ChatMaster时，我们可能会自动收集您的浏览历史、IP地址、使用时间、访问页面、推出时间等信息。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">3.用户信息：我们可能会收集您提供给我们的个人信息，如姓名、电话号码、电子邮件地址等。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">4.交易信息：如果您使用ChatMaster进行在线交易，我们可能会收集与该交易相关的信息，如购买的商品或服务、支付方式、交易金额等。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">二、信息使用</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">我们可能会将收集到的用户信息用于以下用途：</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">1.为用户提供智能对话的服务。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">2.为改进和优化对话的服务内容。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">3.用于向用户推送ChatMaster相关的信息和广告。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">4.用于与用户进行交流，如回复用户的咨询、处理投诉等。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">5.遵守法律法规的要求。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">三、信息共享</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">我们承诺不会将用户的个人信息出售、交换或出租给任何第三方。但以下情况除外：</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">1.用户事先同意共享。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">2.根据法律法规规定，需要与政府机构、执法机构共享用户信息的情况。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">3.为了保护ChatMaster的权益，需要与律师、会计师、评估师等专业人士共享用户信息的情况。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">四、信息安全</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">我们会采取必要的技术手段和管理措施来保护用户的个人信息安全。但由于网络环境的不确定性，我们无法完全保证信息的绝对安全。如果用户发现自己的个人信息存在泄露或被盗用的情况，请立即联系我们，我们会采取必要的措施予以解决。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">五、未成年人保护</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">ChatMaster尤为重视未成年人的隐私保护。如果未成年人在未经监护人同意的情况下使用了我们的服务，我们会尽快采取必要的措施删除相关的个人信息。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">六、隐私政策的修改和更新</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">我们可能会根据需要对本隐私政策进行修改和更新。修改和更新后的隐私政策将在ChatMaster上公布，如用户继续使用本服务，即表示用户已同意修改后的隐私政策。</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">七、联系我们</p><p class=\"ql-align-justify\"><br></p><p class=\"ql-align-justify\">如果您对本隐私政策有任何疑问、意见或建议，欢迎通过ChatMaster内的联系方式与我们取得联系，我们会尽快回复您的问题。</p>', 0);
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='AI助理功能';

-- ----------------------------
-- Records of gpt_assistant
-- ----------------------------
BEGIN;
INSERT INTO `gpt_assistant` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `avatar`, `icon`, `title`, `tag`, `main_model`, `description`, `first_message`, `system_prompt`, `type_id`, `sort`, `status`, `deleted`) VALUES (1, 'System', '2023-12-08 06:58:00', 'System', '2024-06-01 15:28:27', '', '💍', '婚礼请柬文案', '婚礼请柬文案', 0, '作为婚礼文案师，请为一对新人撰写以浪漫田园为主题的婚礼请柬。', '', '你是一名资深的婚礼文案师，需要为一对新人撰写一份婚礼请柬。\n主题：[浪漫田园]\n文案要点：[包含新人的名字、婚礼日期、地点、RSVP详情]', 1, 1, 1, 0);
INSERT INTO `gpt_assistant` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `avatar`, `icon`, `title`, `tag`, `main_model`, `description`, `first_message`, `system_prompt`, `type_id`, `sort`, `status`, `deleted`) VALUES (2, 'System', '2023-12-08 07:00:57', 'System', '2024-06-01 15:28:29', '', '🙇🏻‍♀️', '高效复习计划', '高效复习计划', 0, '线性代数95分冲刺：30天复习攻略', '', '你是一名面临重要考试的大学生，需要在最后一个月内高效复习。\n请根据以下要求制定你的复习计划：\n复习目标：[线性代数获得95分]\n复习时间：[每天1小时]\n要求：\n明确指出具体的复习目标，包括所有需要掌握的科目和知识点。\n将复习目标细化为每周或每天的具体任务，给出整个月都复习排期表，以便跟踪进度。', 2, 1, 1, 0);
INSERT INTO `gpt_assistant` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `avatar`, `icon`, `title`, `tag`, `main_model`, `description`, `first_message`, `system_prompt`, `type_id`, `sort`, `status`, `deleted`) VALUES (3, 'System', '2023-12-08 07:02:15', 'System', '2024-06-01 15:28:31', '', '🚀', '职业规划', '职业规划', 0, 'AI行业大爆发，快来定制专属职业发展路径，助你在AI获得高薪！', '', '你是一名职业规划师，专注于AI行业，请根据以下要求设计个性化职业规划：\n当前职业定位：[交友软件产品经理]\n未来职业目标：[成为AI行业资深产品经理]\n必备技能和知识：[请根据市面上的招聘要求告诉我达成我的目标必备的知识和技能]\n行动计划：[请提供具体可执行的计划]', 3, 1, 1, 0);
INSERT INTO `gpt_assistant` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `avatar`, `icon`, `title`, `tag`, `main_model`, `description`, `first_message`, `system_prompt`, `type_id`, `sort`, `status`, `deleted`) VALUES (4, 'System', '2023-12-08 07:04:54', 'System', '2024-06-01 15:28:35', '', '📕', '探店笔记', '探店笔记', 0, '快速记录店铺体验，分享你的发现与感受，吸引更多关注和互动！', '', '请根据以下要求撰写一篇探店笔记：\n餐厅名称：[老街角意式餐厅]\n特色菜品：[奶酪培根意面和烤羊排]\n顾客体验：[温馨的店内装饰和亲切的服务员]\n要求：[标题参照小红书爆款标题，要吸引人眼球，语言要生动活泼，像是和好朋友热情地分享]', 5, 1, 1, 0);
INSERT INTO `gpt_assistant` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `avatar`, `icon`, `title`, `tag`, `main_model`, `description`, `first_message`, `system_prompt`, `type_id`, `sort`, `status`, `deleted`) VALUES (5, 'System', '2023-12-08 07:07:10', 'System', '2024-06-01 15:28:37', '', '🏁', '秒杀神器代码', '秒杀神器代码', 0, '帮你写Python代码，轻松秒杀购物节', '', '你是一位擅长编写各种程序的资深工程师，我需要你根据以下需求，为我生成一段Python代码：\n代码目的：[设计一个双十一自动抢购脚本]\n代码功能：[在双十一当天自动登录购物网站，定时抢购预设商品]\n代码要求：[使用面向对象编程方式，使用selenium库进行网页操作]', 7, 1, 1, 0);
INSERT INTO `gpt_assistant` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `avatar`, `icon`, `title`, `tag`, `main_model`, `description`, `first_message`, `system_prompt`, `type_id`, `sort`, `status`, `deleted`) VALUES (6, 'System', '2023-12-08 07:08:18', 'System', '2024-06-01 15:28:40', '', '💌', '情感分析', '情感分析', 0, '男朋友长时间不回信息怎么办？一定要忍住', '', '你是一名情感分析师，请为以下要求分析情况并提供建议：\n问题描述：[男朋友回消息很慢]\n分析要求：[分析可能的情况，给出合理的建议和解决方法]', 8, 1, 1, 0);
INSERT INTO `gpt_assistant` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `avatar`, `icon`, `title`, `tag`, `main_model`, `description`, `first_message`, `system_prompt`, `type_id`, `sort`, `status`, `deleted`) VALUES (7, 'System', '2023-12-08 07:10:31', 'System', '2024-06-01 15:28:42', '', '💡', '创意广告软文', '创意广告软文', 0, '灵感涌现，助你轻松编写广告软文。', '', '你是一位出色的软文写手，请根据以下要求，为一个产品撰写软性广告文章：\n产品名称：[X品牌洗面奶]\n目标用户：[20-35岁的都市女性]\n内容要求：[自然融入产品参数和功能解读，结合用户故事或产品体验]\n创作要点：[隐蔽推广，文章主题与产品关联不明显]', 10, 1, 1, 0);
INSERT INTO `gpt_assistant` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `avatar`, `icon`, `title`, `tag`, `main_model`, `description`, `first_message`, `system_prompt`, `type_id`, `sort`, `status`, `deleted`) VALUES (8, 'System', '2023-12-08 07:12:29', 'System', '2024-06-01 15:28:44', '', '🤖', '钢铁侠', '钢铁侠', 0, '画出钢铁侠飞行在夜幕下的纽约', '', '画一个钢铁侠飞行在纽约上空的画面，夜景中建筑灯光闪烁，全景构图，平视视角。', 11, 1, 1, 0);
INSERT INTO `gpt_assistant` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `avatar`, `icon`, `title`, `tag`, `main_model`, `description`, `first_message`, `system_prompt`, `type_id`, `sort`, `status`, `deleted`) VALUES (9, 'System', '2023-12-08 08:03:55', 'System', '2024-06-01 15:28:46', '', '👨‍👩‍👧', '育儿活动策划', '育儿活动策划', 0, '请策划一项探索自然奥秘的育儿活动', '', '你是一名育儿顾问，请根据以下要求策划一项增添趣味的育儿活动：\n活动主题：[探索自然的奥秘]\n目标年龄段：[4-6岁的儿童]\n活动时长：[1小时]', 4, 1, 1, 0);
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
INSERT INTO `gpt_assistant_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `sort`, `status`, `deleted`) VALUES (2, 'admin', '2023-11-22 11:15:29', 'System', '2024-05-31 10:15:28', '学生', 'ph:student-fill', 0, 1, 0);
INSERT INTO `gpt_assistant_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `sort`, `status`, `deleted`) VALUES (3, 'admin', '2023-11-22 11:15:36', 'System', '2024-05-31 10:24:36', '职场', 'healthicons:city-worker', 0, 1, 0);
INSERT INTO `gpt_assistant_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `sort`, `status`, `deleted`) VALUES (4, 'admin', '2023-11-22 11:15:45', 'System', '2024-05-31 10:17:18', '家长', 'material-symbols:family-home-outline', 0, 1, 0);
INSERT INTO `gpt_assistant_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `sort`, `status`, `deleted`) VALUES (5, 'admin', '2023-11-22 11:15:51', 'System', '2024-05-31 10:24:34', '媒体', 'hugeicons:medium', 95, 1, 0);
INSERT INTO `gpt_assistant_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `sort`, `status`, `deleted`) VALUES (6, 'admin', '2023-11-22 11:16:00', 'System', '2024-05-31 10:15:54', '老师', 'mdi:teacher', 0, 1, 0);
INSERT INTO `gpt_assistant_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `sort`, `status`, `deleted`) VALUES (7, 'admin', '2023-11-22 11:16:13', 'System', '2024-05-31 10:24:40', '程序', 'ri:code-box-line', 96, 1, 0);
INSERT INTO `gpt_assistant_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `sort`, `status`, `deleted`) VALUES (8, 'admin', '2023-11-22 11:16:24', 'System', '2024-05-31 10:14:01', '摸鱼大师', 'material-symbols-light:family-star', 98, 1, 0);
INSERT INTO `gpt_assistant_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `sort`, `status`, `deleted`) VALUES (9, 'admin', '2023-11-22 11:16:51', 'admin', '2024-05-31 10:24:32', '生活', 'mdi:family', 99, 1, 0);
INSERT INTO `gpt_assistant_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `sort`, `status`, `deleted`) VALUES (10, 'System', '2023-12-08 07:10:00', 'System', '2024-05-31 10:24:38', '营销', 'mdi:cart-sale', 0, 1, 0);
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
-- Records of gpt_chat
-- ----------------------------
BEGIN;
COMMIT;

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
-- Records of gpt_chat_message
-- ----------------------------
BEGIN;
COMMIT;

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
  `local_model_type` int(1) NOT NULL DEFAULT '0' COMMENT '本地模型类型：1、Langchian；2、ollama；3、Giteeai',
  `model_url` varchar(255) NOT NULL DEFAULT '' COMMENT '模型接口',
  `knowledge` varchar(255) NOT NULL DEFAULT '' COMMENT '知识库名称',
  `version` varchar(64) NOT NULL DEFAULT '' COMMENT '模型版本',
  `sort` int(1) NOT NULL DEFAULT '0' COMMENT '排序',
  `status` smallint(6) NOT NULL DEFAULT '1' COMMENT '状态 0 禁用 1 启用',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='大模型信息';

-- ----------------------------
-- Records of gpt_model
-- ----------------------------
BEGIN;
INSERT INTO `gpt_model` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `model`, `local_model_type`, `model_url`, `knowledge`, `version`, `sort`, `status`, `deleted`) VALUES (1, 'System', '2023-12-01 01:23:51', 'System', '2024-09-14 15:52:55', 'ChatGPT', '', 'ChatGPT', 0, '', '', 'gpt-3.5-turbo-0613', 0, 1, 0);
INSERT INTO `gpt_model` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `model`, `local_model_type`, `model_url`, `knowledge`, `version`, `sort`, `status`, `deleted`) VALUES (2, 'System', '2023-12-01 01:24:43', 'System', '2024-09-14 15:52:55', '文心一言', '', 'WENXIN', 0, '', '', 'ERNIE_Bot_turbo', 0, 1, 0);
INSERT INTO `gpt_model` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `model`, `local_model_type`, `model_url`, `knowledge`, `version`, `sort`, `status`, `deleted`) VALUES (3, 'System', '2023-12-01 01:25:18', 'System', '2024-09-14 15:52:55', '通义千问', '', 'QIANWEN', 0, '', '', 'qwen-turbo', 0, 1, 0);
INSERT INTO `gpt_model` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `model`, `local_model_type`, `model_url`, `knowledge`, `version`, `sort`, `status`, `deleted`) VALUES (4, 'System', '2023-12-01 01:25:29', 'System', '2025-02-08 14:49:57', '讯飞星火', '', 'SPARK', 0, '', '', 'v1.1', 0, 1, 0);
INSERT INTO `gpt_model` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `model`, `local_model_type`, `model_url`, `knowledge`, `version`, `sort`, `status`, `deleted`) VALUES (5, 'System', '2023-12-27 00:38:20', 'admin', '2024-09-14 15:52:55', '智谱清言', '', 'ChatGLM', 0, '', '', 'chatGLM_6b_SSE', 0, 1, 0);
INSERT INTO `gpt_model` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `model`, `local_model_type`, `model_url`, `knowledge`, `version`, `sort`, `status`, `deleted`) VALUES (6, 'System', '2024-05-23 21:30:06', 'admin', '2025-02-10 16:29:00', '月之暗面', '', 'Moonshot', 0, '', '', 'moonshot-v1-8k', 0, 1, 0);
INSERT INTO `gpt_model` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `model`, `local_model_type`, `model_url`, `knowledge`, `version`, `sort`, `status`, `deleted`) VALUES (7, 'System', '2024-05-23 21:30:13', 'admin', '2025-02-10 16:28:56', '书生·浦语', '', 'Internlm', 0, '', '', 'internlm2-latest', 0, 1, 0);
INSERT INTO `gpt_model` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `icon`, `model`, `local_model_type`, `model_url`, `knowledge`, `version`, `sort`, `status`, `deleted`) VALUES (8, 'admin', '2024-09-14 16:02:10', 'admin', '2025-02-10 16:29:07', '本地模型', '', 'LocalLM', 1, 'http://127.0.0.1:7861', '', 'chatglm3-6b', 0, 1, 0);
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
  `model` varchar(32) NOT NULL DEFAULT '' COMMENT '模型',
  `app_id` varchar(64) NOT NULL DEFAULT '' COMMENT 'appid',
  `app_key` varchar(512) NOT NULL DEFAULT '' COMMENT 'app key对应openai的token',
  `app_secret` varchar(128) NOT NULL DEFAULT '' COMMENT 'app密钥',
  `total_tokens` bigint(20) NOT NULL DEFAULT '0' COMMENT '总额度',
  `used_tokens` bigint(20) NOT NULL DEFAULT '0' COMMENT '已用额度',
  `surplus_tokens` bigint(20) NOT NULL DEFAULT '0' COMMENT '剩余token',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态 0 禁用 1 启用',
  `remark` varchar(250) DEFAULT '' COMMENT '备注',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`),
  KEY `app_key` (`app_key`),
  KEY `model` (`model`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='openai token';

-- ----------------------------
-- Records of gpt_openkey
-- ----------------------------
BEGIN;
INSERT INTO `gpt_openkey` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `model`, `app_id`, `app_key`, `app_secret`, `total_tokens`, `used_tokens`, `surplus_tokens`, `status`, `remark`, `deleted`) VALUES (1, 'admin', '2023-05-04 10:10:05', 'root', '2025-02-10 16:27:39', 'ChatGPT', '', '', '', 100000, 0, 0, 1, 'master@gmail.com', 0);
INSERT INTO `gpt_openkey` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `model`, `app_id`, `app_key`, `app_secret`, `total_tokens`, `used_tokens`, `surplus_tokens`, `status`, `remark`, `deleted`) VALUES (2, 'System', '2023-09-07 09:20:48', 'System', '2025-02-10 16:27:48', 'WENXIN', '', '', '', 100000, 0, 0, 1, '', 0);
INSERT INTO `gpt_openkey` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `model`, `app_id`, `app_key`, `app_secret`, `total_tokens`, `used_tokens`, `surplus_tokens`, `status`, `remark`, `deleted`) VALUES (3, 'System', '2023-09-12 02:24:22', 'System', '2025-02-10 16:27:39', 'QIANWEN', '', '', '', 100000, 0, 0, 1, '', 0);
INSERT INTO `gpt_openkey` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `model`, `app_id`, `app_key`, `app_secret`, `total_tokens`, `used_tokens`, `surplus_tokens`, `status`, `remark`, `deleted`) VALUES (4, 'System', '2023-09-12 02:25:27', 'master', '2025-02-10 16:27:48', 'SPARK', '', '', '', 100000, 0, 0, 1, '', 0);
INSERT INTO `gpt_openkey` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `model`, `app_id`, `app_key`, `app_secret`, `total_tokens`, `used_tokens`, `surplus_tokens`, `status`, `remark`, `deleted`) VALUES (5, 'System', '2023-12-27 00:37:59', 'System', '2025-02-10 16:27:48', 'ChatGLM', '', '', '', 100000, 0, 0, 1, '', 0);
INSERT INTO `gpt_openkey` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `model`, `app_id`, `app_key`, `app_secret`, `total_tokens`, `used_tokens`, `surplus_tokens`, `status`, `remark`, `deleted`) VALUES (6, 'System', '2024-05-23 21:30:53', 'System', '2025-02-10 16:27:39', 'Moonshot', '', '', '', 100000, 0, 0, 1, '', 0);
INSERT INTO `gpt_openkey` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `model`, `app_id`, `app_key`, `app_secret`, `total_tokens`, `used_tokens`, `surplus_tokens`, `status`, `remark`, `deleted`) VALUES (7, 'System', '2024-05-23 21:31:02', 'System', '2025-02-10 16:27:39', 'Internlm', '', '', '', 100000, 0, 0, 1, '', 0);
INSERT INTO `gpt_openkey` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `model`, `app_id`, `app_key`, `app_secret`, `total_tokens`, `used_tokens`, `surplus_tokens`, `status`, `remark`, `deleted`) VALUES (8, 'admin', '2024-09-19 15:58:35', 'admin', '2025-02-10 16:27:53', 'LocalLM', '', '', '', 100000, 0, 0, 1, '', 0);
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员用户';

-- ----------------------------
-- Records of gpt_user
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(256) NOT NULL DEFAULT '' COMMENT '客户端id',
  `client_secret` varchar(256) DEFAULT '' COMMENT '客户端密钥',
  `resource_ids` varchar(256) DEFAULT '' COMMENT '可访问资源id',
  `scope` varchar(256) DEFAULT '' COMMENT '权限范围',
  `authorized_grant_types` varchar(256) DEFAULT 'password,refresh_token,authorization_code,implicit' COMMENT '授权模式 password,refresh_token,authorization_code,implicit',
  `web_server_redirect_uri` varchar(256) DEFAULT '' COMMENT '授权回调地址',
  `authorities` varchar(256) DEFAULT '' COMMENT '权限值',
  `access_token_validity` int(11) DEFAULT '86400' COMMENT '令牌过期秒数 默认一天',
  `refresh_token_validity` int(11) DEFAULT '604800' COMMENT '刷新令牌过期秒数 默认一星期',
  `additional_information` varchar(4096) DEFAULT '' COMMENT '附加说明',
  `autoapprove` varchar(256) DEFAULT 'false' COMMENT '自动授权',
  `remark` varchar(256) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='认证授权表';

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_base_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_base_config`;
CREATE TABLE `sys_base_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '配置键值',
  `data` longtext COMMENT '配置内容',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='基础配置';

-- ----------------------------
-- Records of sys_base_config
-- ----------------------------
BEGIN;
INSERT INTO `sys_base_config` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `data`, `deleted`) VALUES (1, 'admin', '2024-01-22 10:38:39', 'root', '2024-12-09 18:56:57', 'baseInfo', '{\"contentSecurity\":0,\"copyright\":\"© 2023 ChatMASTER 苏ICP备66668888号 江苏Master科技有限公司\",\"descrip\":\"ChatMASTER，基于AI大模型api实现的ChatGPT服务，支持ChatGPT(3.5、4.0)模型，同时也支持国内文心一言(支持Stable-Diffusion-XL作图)、通义千问、讯飞星火、智谱清言(ChatGLM)等主流模型，支出同步响应及流式响应，完美呈现打印机效果。\",\"keywords\":[\"通义千问\",\"ChatGPT\",\"文心一言\",\"智谱清言\"],\"proxyServer\":\"\",\"siteTitle\":\"ChatMASTER\",\"domain\":\"https://gpt.panday94.xyz\",\"proxyType\":2,\"siteLogo\":\"\"}', 0);
INSERT INTO `sys_base_config` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `data`, `deleted`) VALUES (2, 'admin', '2024-01-22 10:39:48', 'root', '2024-06-01 16:54:43', 'appInfo', '{\"h5Url\":\"https://gpt.panday94.xyz/h5\",\"isSms\":1,\"homeNotice\":\"确保合法合规使用，在运营过程中产生的一切问题后果自负，与作者无关。!\",\"isGptLimit\":0,\"isShare\":1,\"shareRewardNum\":\"20\",\"freeNum\":\"5\",\"isRedemption\":1}', 0);
INSERT INTO `sys_base_config` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `data`, `deleted`) VALUES (3, 'admin', '2024-01-22 10:39:53', 'System', '2024-01-22 10:39:53', 'wxInfo', '{\"mpLogin\":0,\"mpPay\":0,\"maAppId\":\"xx\",\"maSecret\":\"xx\",\"mpAppId\":\"xx\",\"mpSecret\":\"xx\",\"mchNo\":\"xx\",\"v3Secret\":\"xx\"}', 0);
INSERT INTO `sys_base_config` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `data`, `deleted`) VALUES (4, 'admin', '2024-01-22 10:40:04', 'root', '2024-05-31 15:30:16', 'extraInfo', '{\"ossType\":1,\"smsType\":0}', 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(100) DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) DEFAULT '' COMMENT '参数键值',
  `type` int(1) DEFAULT '1' COMMENT '系统内置（1是 0否）',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  `remark` varchar(512) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='参数配置表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
BEGIN;
INSERT INTO `sys_config` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `config_key`, `config_value`, `type`, `deleted`, `remark`) VALUES (1, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-blue', 1, 0, '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow');
INSERT INTO `sys_config` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `config_key`, `config_value`, `type`, `deleted`, `remark`) VALUES (2, 'admin', '2022-07-08 15:40:23', 'admin', '2022-07-08 15:40:22', '用户管理-账号初始密码', 'sys.user.initPassword', '123456', 1, 0, '初始化密码 123456');
INSERT INTO `sys_config` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `config_key`, `config_value`, `type`, `deleted`, `remark`) VALUES (3, 'admin', '2022-07-08 15:40:23', 'admin', '2022-07-08 15:40:22', '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-dark', 1, 0, '深色主题theme-dark，浅色主题theme-light');
INSERT INTO `sys_config` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `config_key`, `config_value`, `type`, `deleted`, `remark`) VALUES (4, 'admin', '2022-07-08 15:40:23', 'admin', '2022-07-15 21:00:22', '账号自助-验证码开关', 'sys.account.captchaOnOff', 'true', 1, 0, '是否开启验证码功能（true开启，false关闭）');
INSERT INTO `sys_config` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `config_key`, `config_value`, `type`, `deleted`, `remark`) VALUES (5, 'admin', '2022-07-08 15:40:23', 'admin', '2022-07-08 15:40:22', '账号自助-是否开启用户注册功能', 'sys.account.registerUser', 'false', 1, 0, '是否开启注册用户功能（true开启，false关闭）');
INSERT INTO `sys_config` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `config_key`, `config_value`, `type`, `deleted`, `remark`) VALUES (6, 'admin', '2022-08-02 17:31:37', 'admin', '2022-08-02 17:31:37', '账号自助-是否允许同时登录', 'sys.account.allLogin', 'false', 1, 0, '是否允许同时登录（true开启，false关闭）');
INSERT INTO `sys_config` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `config_key`, `config_value`, `type`, `deleted`, `remark`) VALUES (7, 'admin', '2022-08-16 15:58:54', 'admin', '2022-08-16 15:58:54', '即使通讯-是否开启IM模块', 'sys.module.IM', 'false', 1, 0, '是否开启IM模块（true开启，false关闭）');
COMMIT;

-- ----------------------------
-- Table structure for sys_date
-- ----------------------------
DROP TABLE IF EXISTS `sys_date`;
CREATE TABLE `sys_date` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `date_time` datetime DEFAULT NULL COMMENT '日期时间',
  `date_string` varchar(32) NOT NULL DEFAULT '' COMMENT '日期',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `date_string` (`date_string`) USING BTREE,
  UNIQUE KEY `date_time` (`date_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='日期';

-- ----------------------------
-- Records of sys_date
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(128) NOT NULL DEFAULT '' COMMENT '部门名称',
  `sys_user_id` bigint(20) DEFAULT '0' COMMENT '部门负责人id',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '父节点id',
  `tree_path` varchar(32) NOT NULL DEFAULT '' COMMENT '父节点路径 如: 1; 1-2; 1-2-3',
  `level` int(1) NOT NULL DEFAULT '1' COMMENT '节点等级',
  `sort` int(1) NOT NULL DEFAULT '0' COMMENT '排序',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态 0->禁用;1->启用',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='部门';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `sys_user_id`, `parent_id`, `tree_path`, `level`, `sort`, `status`, `deleted`) VALUES (1, 'System', '2022-07-21 10:01:52', 'System', '2022-07-21 10:01:52', '集团', 1, 0, '1', 1, 0, 1, 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_dept_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept_role`;
CREATE TABLE `sys_dept_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `dept_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '部门id',
  `role_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色id',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态 0->禁用;1->启用',
  PRIMARY KEY (`id`),
  KEY `dept_id` (`dept_id`),
  KEY `role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门角色';

-- ----------------------------
-- Records of sys_dept_role
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `label` varchar(128) DEFAULT '' COMMENT '字典标签',
  `value` varchar(128) DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(128) DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(128) DEFAULT '' COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(128) DEFAULT '' COMMENT '表格回显样式',
  `is_default` int(1) DEFAULT '0' COMMENT '是否默认 0->否 1->是',
  `sort` int(1) DEFAULT '0' COMMENT '字典排序',
  `status` int(1) DEFAULT '1' COMMENT '状态 0->禁用;1->启用',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  `remark` varchar(512) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `dict_type` (`dict_type`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='字典数据表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (1, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '男', '1', 'sys_user_sex', '', '', 1, 1, 1, 0, '性别男');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (2, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '女', '0', 'sys_user_sex', '', '', 0, 2, 1, 0, '性别女');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (3, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-15 14:35:41', '未知', '-1', 'sys_user_sex', '', '', 0, 3, 1, 0, '性别未知');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (4, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-15 19:46:07', '显示', '0', 'sys_show_hide', '', 'primary', 1, 1, 1, 0, '显示菜单');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (5, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-15 19:46:11', '隐藏', '1', 'sys_show_hide', '', 'danger', 0, 2, 1, 0, '隐藏菜单');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (6, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-15 14:35:12', '启用', '1', 'sys_normal_disable', '', 'primary', 1, 1, 1, 0, '正常状态');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (7, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-15 14:35:24', '禁用', '0', 'sys_normal_disable', '', 'danger', 0, 2, 1, 0, '停用状态');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (8, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-15 19:46:29', '正常', '1', 'sys_job_status', '', 'primary', 1, 1, 1, 0, '正常状态');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (9, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-15 19:46:37', '暂停', '0', 'sys_job_status', '', 'danger', 0, 2, 1, 0, '停用状态');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (10, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '默认', 'DEFAULT', 'sys_job_group', '', '', 1, 1, 1, 0, '默认分组');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (11, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '系统', 'SYSTEM', 'sys_job_group', '', '', 0, 2, 1, 0, '系统分组');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (12, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-20 08:11:41', '是', '1', 'sys_yes_no', '', 'primary', 1, 1, 1, 0, '系统默认是');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (13, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-20 08:11:45', '否', '0', 'sys_yes_no', '', 'danger', 0, 2, 1, 0, '系统默认否');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (14, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '通知', '1', 'sys_notice_type', '', 'warning', 1, 1, 1, 0, '通知');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (15, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '公告', '2', 'sys_notice_type', '', 'success', 0, 2, 1, 0, '公告');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (16, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-15 19:46:44', '正常', '1', 'sys_notice_status', '', 'primary', 1, 1, 1, 0, '正常状态');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (17, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-15 19:46:48', '关闭', '0', 'sys_notice_status', '', 'danger', 0, 2, 1, 0, '关闭状态');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (18, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-21 00:29:45', '新增', 'INSERT', 'sys_oper_type', '', 'info', 0, 1, 1, 0, '新增操作');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (19, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-21 00:29:49', '修改', 'UPDATE', 'sys_oper_type', '', 'info', 0, 2, 1, 0, '修改操作');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (20, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-21 00:29:54', '删除', 'DELETE', 'sys_oper_type', '', 'danger', 0, 3, 1, 0, '删除操作');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (21, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-21 00:29:59', '授权', 'GRANT', 'sys_oper_type', '', 'primary', 0, 4, 1, 0, '授权操作');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (22, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-21 00:30:07', '导出', 'EXPORT', 'sys_oper_type', '', 'warning', 0, 5, 1, 0, '导出操作');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (23, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-21 00:30:13', '导入', 'IMPORT', 'sys_oper_type', '', 'warning', 0, 6, 1, 0, '导入操作');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (24, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-21 00:30:17', '强退', 'FORCE', 'sys_oper_type', '', 'danger', 0, 7, 1, 0, '强退操作');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (25, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-21 00:30:23', '生成代码', 'GENCODE', 'sys_oper_type', '', 'warning', 0, 8, 1, 0, '生成操作');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (26, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-21 00:30:28', '清空数据', 'CLEAN', 'sys_oper_type', '', 'danger', 0, 9, 1, 0, '清空操作');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (27, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-15 19:46:51', '成功', '1', 'sys_common_status', '', 'primary', 0, 1, 1, 0, '正常状态');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (28, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-15 19:46:55', '失败', '0', 'sys_common_status', '', 'danger', 0, 2, 1, 0, '停用状态');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (29, 'admin', '2023-05-04 11:25:27', 'System', '2023-05-04 11:25:27', '用户协议', '1', 'gpt_content_type', '', 'default', 0, 1, 1, 0, '');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (30, 'admin', '2023-05-04 11:25:35', 'admin', '2023-05-04 11:25:35', '隐私政策', '2', 'gpt_content_type', '', 'default', 0, 2, 1, 0, '');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (31, 'admin', '2023-05-04 11:25:45', 'System', '2023-05-04 11:25:45', '使用指南', '3', 'gpt_content_type', '', 'default', 0, 3, 1, 0, '');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (32, 'admin', '2023-05-04 11:32:45', 'System', '2023-05-04 11:32:45', '次数', '1', 'gpt_comb_type', '', 'default', 0, 1, 1, 0, '');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (33, 'admin', '2023-05-04 11:32:53', 'System', '2023-05-04 11:32:53', '天数', '2', 'gpt_comb_type', '', 'default', 0, 2, 1, 0, '');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (34, 'admin', '2023-05-06 11:55:48', 'System', '2023-05-06 11:55:48', '微信小程序', '1', 'gpt_member_type', '', 'primary', 0, 1, 1, 0, '');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (35, 'admin', '2023-05-06 11:56:00', 'System', '2023-05-06 11:56:00', '公众号', '2', 'gpt_member_type', '', 'success', 0, 2, 1, 0, '');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (36, 'admin', '2023-05-06 11:56:11', 'admin', '2023-05-06 11:56:11', '手机号', '3', 'gpt_member_type', '', 'warning', 0, 3, 1, 0, '');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (37, 'admin', '2023-05-06 11:57:41', 'System', '2023-05-06 11:57:41', '回复中', '1', 'gpt_chat_status', '', 'warning', 0, 1, 1, 0, '');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (38, 'admin', '2023-05-06 11:57:55', 'System', '2023-05-06 11:57:55', '回复成功', '2', 'gpt_chat_status', '', 'success', 0, 2, 1, 0, '');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (39, 'admin', '2023-05-06 11:58:07', 'System', '2023-05-06 11:58:07', '回复失败', '3', 'gpt_chat_status', '', 'danger', 0, 3, 1, 0, '');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (40, 'admin', '2023-09-06 15:33:28', 'root', '2023-09-06 15:33:28', 'ChatGPT', 'ChatGPT', 'gpt_model_type', '', 'primary', 0, 1, 1, 0, '');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (41, 'admin', '2024-01-22 09:15:57', 'admin', '2024-01-22 01:45:58', '本地上传', '1', 'sys_oss_type', '', 'primary', 0, 1, 1, 0, '');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (42, 'admin', '2024-01-22 09:16:46', 'admin', '2024-01-22 01:46:06', '阿里OSS', '2', 'sys_oss_type', '', 'success', 0, 2, 1, 0, '');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (43, 'admin', '2024-01-22 09:17:06', 'System', '2024-01-22 01:46:06', '腾讯COS', '3', 'sys_oss_type', '', 'warning', 0, 3, 1, 0, '');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (44, 'admin', '2024-01-22 09:30:24', 'admin', '2024-01-22 09:30:24', '无', '0', 'sys_sms_type', '', 'info', 0, 1, 1, 0, '');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (45, 'admin', '2024-01-22 09:30:37', 'admin', '2024-01-22 09:30:37', '阿里云SMS', '1', 'sys_sms_type', '', 'success', 0, 1, 1, 0, '');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (46, 'admin', '2024-01-22 15:37:59', 'System', '2024-03-13 20:51:59', '腾讯云SMS', '2', 'sys_sms_type', '', 'success', 0, 2, 1, 0, '');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (47, 'admin', '2024-01-22 15:37:59', 'System', '2024-03-14 11:09:34', '文心一言', 'WENXIN', 'gpt_model_type', '', 'success', 0, 2, 1, 0, '');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (48, 'admin', '2024-01-22 15:38:17', 'System', '2024-03-14 11:09:37', '通义千问', 'QIANWEN', 'gpt_model_type', '', 'info', 0, 3, 1, 0, '');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (49, 'admin', '2024-01-22 15:38:34', 'System', '2024-03-14 11:09:39', '讯飞星火', 'SPARK', 'gpt_model_type', '', 'warning', 0, 4, 1, 0, '');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (50, 'admin', '2024-01-22 15:39:03', 'root', '2024-03-14 11:09:44', '智谱清言', 'ChatGLM', 'gpt_model_type', '', 'danger', 0, 5, 1, 0, '');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (51, 'root', '2024-05-31 09:21:50', 'root', '2024-05-31 09:21:50', '月之暗面', 'Moonshot', 'gpt_model_type', '', 'warning', 0, 6, 1, 0, '');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (52, 'root', '2024-05-31 09:22:08', 'admin', '2024-05-31 09:22:08', '书生浦语', 'Internlm', 'gpt_model_type', '', 'primary', 0, 7, 1, 0, '');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (53, 'admin', '2024-07-19 15:30:50', 'admin', '2024-07-19 15:30:50', '本地模型', 'LocalLM', 'gpt_model_type', '', 'info', 0, 99, 1, 0, '');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (54, 'admin', '2024-09-14 15:50:34', 'admin', '2024-09-14 15:50:34', 'Langchain', '1', 'gpt_local_model', '', 'default', 0, 1, 1, 0, '');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (55, 'admin', '2024-09-14 15:50:52', 'admin', '2024-09-14 15:50:52', 'Ollama', '2', 'gpt_local_model', '', 'default', 0, 2, 1, 0, '');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (56, 'admin', '2024-09-14 15:51:20', 'admin', '2024-09-14 15:51:20', 'GiteeAI', '3', 'gpt_local_model', '', 'default', 0, 3, 1, 0, '');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (57, 'admin', '2024-09-19 15:58:02', 'System', '2024-09-19 15:58:02', '扣子', '4', 'gpt_local_model', '', 'default', 0, 4, 1, 0, '');
INSERT INTO `sys_dict` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `label`, `value`, `dict_type`, `css_class`, `list_class`, `is_default`, `sort`, `status`, `deleted`, `remark`) VALUES (58, 'admin', '2024-09-19 15:58:13', 'System', '2024-09-19 15:58:13', 'FastGPT', '5', 'gpt_local_model', '', 'default', 0, 5, 1, 0, '');
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(128) DEFAULT '' COMMENT '字典名称',
  `type` varchar(128) DEFAULT '' COMMENT '字典类型',
  `status` int(1) DEFAULT '0' COMMENT '状态 0->禁用;1->启用',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `type` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='字典类型表';

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `type`, `status`, `deleted`) VALUES (1, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '用户性别', 'sys_user_sex', 1, 0);
INSERT INTO `sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `type`, `status`, `deleted`) VALUES (2, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '菜单状态', 'sys_show_hide', 1, 0);
INSERT INTO `sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `type`, `status`, `deleted`) VALUES (3, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '系统开关', 'sys_normal_disable', 1, 0);
INSERT INTO `sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `type`, `status`, `deleted`) VALUES (4, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '任务状态', 'sys_job_status', 1, 0);
INSERT INTO `sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `type`, `status`, `deleted`) VALUES (5, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '任务分组', 'sys_job_group', 1, 0);
INSERT INTO `sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `type`, `status`, `deleted`) VALUES (6, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '系统是否', 'sys_yes_no', 1, 0);
INSERT INTO `sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `type`, `status`, `deleted`) VALUES (7, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '通知类型', 'sys_notice_type', 1, 0);
INSERT INTO `sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `type`, `status`, `deleted`) VALUES (8, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '通知状态', 'sys_notice_status', 1, 0);
INSERT INTO `sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `type`, `status`, `deleted`) VALUES (9, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '操作类型', 'sys_oper_type', 1, 0);
INSERT INTO `sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `type`, `status`, `deleted`) VALUES (10, 'admin', '2022-07-08 15:40:22', 'admin', '2022-07-08 15:40:22', '系统状态', 'sys_common_status', 1, 0);
INSERT INTO `sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `type`, `status`, `deleted`) VALUES (11, 'admin', '2023-05-04 11:24:39', 'System', '2023-05-04 11:24:39', '内容类型', 'gpt_content_type', 1, 0);
INSERT INTO `sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `type`, `status`, `deleted`) VALUES (12, 'admin', '2023-05-04 11:32:35', 'System', '2023-05-04 11:32:35', '套餐类型', 'gpt_comb_type', 1, 0);
INSERT INTO `sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `type`, `status`, `deleted`) VALUES (13, 'admin', '2023-05-06 11:55:35', 'System', '2023-05-06 11:55:35', '用户类型', 'gpt_member_type', 1, 0);
INSERT INTO `sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `type`, `status`, `deleted`) VALUES (14, 'admin', '2023-05-06 11:57:25', 'System', '2023-05-06 11:57:25', '聊天状态', 'gpt_chat_status', 1, 0);
INSERT INTO `sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `type`, `status`, `deleted`) VALUES (15, 'admin', '2023-09-06 15:32:46', 'admin', '2023-09-06 15:32:46', 'gpt模型类型', 'gpt_model_type', 1, 0);
INSERT INTO `sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `type`, `status`, `deleted`) VALUES (16, 'admin', '2024-01-22 09:15:45', 'admin', '2024-01-22 09:15:45', '上传类型', 'sys_oss_type', 1, 0);
INSERT INTO `sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `type`, `status`, `deleted`) VALUES (17, 'admin', '2024-01-22 09:29:29', 'System', '2024-01-22 09:29:29', '短信类型', 'sys_sms_type', 1, 0);
INSERT INTO `sys_dict_type` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `type`, `status`, `deleted`) VALUES (18, 'admin', '2024-09-14 15:50:12', 'System', '2024-09-14 15:50:12', '本地模型', 'gpt_local_model', 1, 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) NOT NULL DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` int(1) NOT NULL DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` int(1) NOT NULL DEFAULT '0' COMMENT '是否并发执行（0禁止 1允许）',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态（0暂停 1正常）',
  `remark` varchar(500) NOT NULL DEFAULT '' COMMENT '备注信息',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `name` (`name`),
  KEY `job_group` (`job_group`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='定时任务调度表';

-- ----------------------------
-- Records of sys_job
-- ----------------------------
BEGIN;
INSERT INTO `sys_job` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `job_group`, `invoke_target`, `cron_expression`, `misfire_policy`, `concurrent`, `status`, `remark`, `deleted`) VALUES (1, 'admin', '2022-07-08 15:40:23', 'admin', '2022-07-08 15:40:23', '系统默认（无参）', 'DEFAULT', 'demoTask.testNoParams()', '0/10 * * * * ?', 3, 1, 0, '', 0);
INSERT INTO `sys_job` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `job_group`, `invoke_target`, `cron_expression`, `misfire_policy`, `concurrent`, `status`, `remark`, `deleted`) VALUES (2, 'admin', '2022-07-08 15:40:23', 'admin', '2022-07-08 15:40:23', '系统默认（有参）', 'DEFAULT', 'demoTask.testParams(\'master\')', '0/15 * * * * ?', 3, 1, 0, '', 0);
INSERT INTO `sys_job` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `job_group`, `invoke_target`, `cron_expression`, `misfire_policy`, `concurrent`, `status`, `remark`, `deleted`) VALUES (3, 'admin', '2022-07-08 15:40:23', 'admin', '2022-07-08 15:40:23', '系统默认（多参）', 'DEFAULT', 'demoTask.testMultipleParams(\'master\', true, 2000L, 316.50D, 100)', '0/20 * * * * ?', 3, 1, 0, '', 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE `sys_job_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `job_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '任务ID',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL DEFAULT '' COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL DEFAULT '' COMMENT '调用目标字符串',
  `job_message` varchar(500) NOT NULL DEFAULT '' COMMENT '日志信息',
  `status` char(1) DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) DEFAULT '' COMMENT '异常信息',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `stop_time` datetime DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='定时任务调度日志表';

-- ----------------------------
-- Records of sys_job_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `sys_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '操作人id',
  `fk_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '操作记录id',
  `username` varchar(64) NOT NULL DEFAULT '' COMMENT '操作人',
  `ip` varchar(64) NOT NULL DEFAULT '' COMMENT '请求ip',
  `address` varchar(64) NOT NULL DEFAULT '' COMMENT '操作地址',
  `domain` varchar(64) NOT NULL DEFAULT '' COMMENT '域名',
  `browser` varchar(64) NOT NULL DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(64) NOT NULL DEFAULT '' COMMENT '操作系统',
  `method` varchar(128) NOT NULL DEFAULT '' COMMENT '请求方法',
  `request_method` varchar(64) NOT NULL DEFAULT '' COMMENT '请求方式',
  `uri` varchar(64) NOT NULL DEFAULT '' COMMENT '接口名称',
  `title` varchar(64) NOT NULL DEFAULT '' COMMENT '操作模块',
  `business_type` varchar(64) NOT NULL DEFAULT '' COMMENT '操作类型',
  `operation` varchar(512) NOT NULL DEFAULT '' COMMENT '操作内容',
  `time` bigint(20) NOT NULL DEFAULT '0' COMMENT '请求耗时',
  `params` text COMMENT '请求参数',
  `result` text COMMENT '执行结果',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `sys_user_id` (`sys_user_id`),
  KEY `fk_id` (`fk_id`),
  KEY `title` (`title`),
  KEY `business_type` (`business_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `expire_time` datetime DEFAULT NULL COMMENT 'token过期时间',
  `sys_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `session_id` varchar(128) NOT NULL DEFAULT '' COMMENT '会话标识',
  `username` varchar(64) NOT NULL DEFAULT '' COMMENT '用户名',
  `ip` varchar(64) NOT NULL DEFAULT '' COMMENT 'ip地址',
  `address` varchar(64) NOT NULL DEFAULT '' COMMENT '登录地址',
  `domain` varchar(64) NOT NULL DEFAULT '' COMMENT '域名',
  `browser` varchar(64) NOT NULL DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(64) NOT NULL DEFAULT '' COMMENT '操作系统',
  `msg` varchar(512) NOT NULL DEFAULT '' COMMENT '登录信息',
  `authorization` text COMMENT '身份标识',
  `user_agent` text COMMENT '浏览器类型',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '登录状态 0 失败 1 成功',
  `enabled` int(1) NOT NULL DEFAULT '1' COMMENT '是否启用 0 禁用 1 启用',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='登录日志';

-- ----------------------------
-- Records of sys_login_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '公告类型（1通知 2公告）',
  `title` varchar(128) NOT NULL DEFAULT '' COMMENT '公告标题',
  `agreement` text COMMENT '公告内容',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态 0->禁用;1->启用',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统通知';

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_notice_read
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice_read`;
CREATE TABLE `sys_notice_read` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `sys_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `notice_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '通知id',
  `is_read` int(1) NOT NULL DEFAULT '1' COMMENT '是否已读 0：未读；1：已读',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统通知已读状态';

-- ----------------------------
-- Records of sys_notice_read
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(128) NOT NULL DEFAULT '' COMMENT '岗位名称',
  `code` varchar(32) NOT NULL DEFAULT '' COMMENT '岗位编码',
  `sort` int(1) NOT NULL DEFAULT '0' COMMENT '排序',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态 0->禁用;1->启用',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='岗位';

-- ----------------------------
-- Records of sys_post
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(128) NOT NULL DEFAULT '' COMMENT '资源名称',
  `title` varchar(128) NOT NULL DEFAULT '' COMMENT '标题名称',
  `code` varchar(128) NOT NULL DEFAULT '' COMMENT '菜单编码',
  `icon` varchar(32) NOT NULL DEFAULT '' COMMENT '菜单图标',
  `path` varchar(64) NOT NULL DEFAULT '' COMMENT '路由路径',
  `component` varchar(64) NOT NULL DEFAULT '' COMMENT '组件路径',
  `query` varchar(64) NOT NULL DEFAULT '' COMMENT '路由参数',
  `perms` varchar(256) NOT NULL DEFAULT '' COMMENT '权限字符串',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '父级id',
  `redirect` int(1) NOT NULL DEFAULT '0' COMMENT '是否重定向 0：否 1: 是',
  `type` int(1) NOT NULL DEFAULT '1' COMMENT '类型：1：目录 2: 菜单 3：操作资源',
  `sort` int(1) NOT NULL DEFAULT '0' COMMENT '排序',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态 0->禁用;1->启用',
  `hidden` int(1) NOT NULL DEFAULT '0' COMMENT '是否隐藏 0->不隐藏;1->隐藏',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1188 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='资源';

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
BEGIN;
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1, 'System', '2022-08-04 15:31:10', 'root', '2022-11-12 15:11:00', '系统管理', '', '', 'system', 'sys', '', '', '', 0, 0, 1, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (2, 'System', '2022-08-04 15:31:10', 'root', '2022-08-04 15:31:10', '系统监控', '', '', 'monitor', 'monitor', '', '', '', 0, 0, 1, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (3, 'System', '2022-08-04 15:31:10', 'root', '2023-04-28 08:05:40', '系统工具', '', '', 'tool', 'tool', '', '', '', 0, 0, 1, 7, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (4, 'System', '2022-08-04 15:31:10', 'root', '2022-08-04 15:31:10', 'ChatMASTER', '', '', 'guide', 'https://gpt.panday94.xyz', '', '', '', 0, 1, 1, 8, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (100, 'System', '2022-08-04 15:31:10', 'admin', '2023-04-19 16:23:21', '用户管理', '', '', 'user', 'user', 'sys/user/index', '', '', 1, 0, 2, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (101, 'System', '2022-08-04 15:31:10', 'admin', '2023-04-19 16:23:21', '角色管理', '', '', 'peoples', 'role', 'sys/role/index', '', '', 1, 0, 2, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (102, 'System', '2022-08-04 15:31:10', 'admin', '2022-11-12 15:09:48', '菜单管理', '', '', 'tree-table', 'resource', 'sys/resource/index', '', '', 1, 0, 2, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (103, 'System', '2022-08-04 15:31:10', 'admin', '2023-04-19 16:23:21', '部门管理', '', '', 'tree', 'dept', 'sys/dept/index', '', '', 1, 0, 2, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (104, 'System', '2022-08-04 15:31:10', 'admin', '2023-04-19 16:23:21', '岗位管理', '', '', 'post', 'post', 'sys/post/index', '', '', 1, 0, 2, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (105, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:02:01', '字典管理', '', '', 'dict', 'dict', 'sys/dict/index', '', '', 1, 0, 2, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (106, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:02:06', '参数设置', '', '', 'edit', 'config', 'sys/config/index', '', '', 1, 0, 2, 7, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (107, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:02:13', '通知公告', '', '', 'message', 'notice', 'sys/notice/index', '', '', 1, 0, 2, 8, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (108, 'System', '2022-08-04 15:31:10', 'System', '2022-08-04 15:31:10', '日志列表', '', '', 'log', 'log', '', '', '', 2, 0, 1, 7, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (109, 'System', '2022-08-04 15:31:10', 'System', '2024-05-30 21:53:57', '在线用户', '', '', 'online', 'online', 'monitor/online/index', '', '', 2, 0, 2, 1, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (110, 'System', '2022-08-04 15:31:10', 'System', '2022-08-04 15:31:10', '定时任务', '', '', 'job', 'job', 'monitor/job/index', '', '', 2, 0, 2, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (111, 'System', '2022-08-04 15:31:10', 'System', '2024-05-30 21:54:31', '数据监控', '', '', 'druid', 'druid', 'monitor/druid/index', '', '', 2, 0, 2, 3, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (112, 'System', '2022-08-04 15:31:10', 'System', '2024-05-30 21:54:34', '服务监控', '', '', 'server', 'server', 'monitor/server/index', '', '', 2, 0, 2, 4, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (113, 'System', '2022-08-04 15:31:10', 'System', '2024-05-30 21:54:37', '缓存监控', '', '', 'redis', 'cache', 'monitor/cache/index', '', '', 2, 0, 2, 5, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (114, 'System', '2022-08-04 15:31:10', 'System', '2024-05-30 21:54:40', '缓存列表', '', '', 'redis-list', 'cacheList', 'monitor/cache/list', '', '', 2, 0, 2, 6, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (115, 'System', '2022-08-04 15:31:10', 'System', '2023-04-28 08:05:40', '表单构建', '', '', 'build', 'build', 'tool/build/index', '', '', 3, 0, 2, 1, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (116, 'System', '2022-08-04 15:31:10', 'System', '2023-04-28 08:05:40', '代码生成', '', '', 'code', 'gen', 'tool/gen/index', '', '', 3, 0, 2, 2, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (117, 'System', '2022-08-04 15:31:10', 'System', '2023-04-28 08:05:40', '接口地址', '', '', 'guide', 'http://yapi.dinggehuo.com', 'tool/swagger/index', '', '', 3, 1, 2, 3, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (500, 'System', '2022-08-04 15:31:10', 'System', '2022-08-04 15:31:10', '操作日志', '', '', 'form', 'operlog', 'monitor/operlog/index', '', '', 108, 0, 2, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (501, 'System', '2022-08-04 15:31:10', 'System', '2022-08-04 15:31:10', '登录日志', '', '', 'logininfor', 'logininfor', 'monitor/logininfor/index', '', '', 108, 0, 2, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1000, 'System', '2022-08-04 15:31:10', 'admin', '2022-11-12 15:03:25', '用户列表', '', '', '#', '#', '', '', 'sys:user:list', 100, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1001, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '用户查询', '', '', '#', '#', '', '', 'sys:user:query', 100, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1002, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '用户新增', '', '', '#', '#', '', '', 'sys:user:save', 100, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1003, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '用户修改', '', '', '#', '#', '', '', 'sys:user:update', 100, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1004, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '用户删除', '', '', '#', '#', '', '', 'sys:user:remove', 100, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1005, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '用户导出', '', '', '#', '#', '', '', 'sys:user:export', 100, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1006, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '用户导入', '', '', '#', '#', '', '', 'sys:user:import', 100, 0, 3, 7, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1007, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '重置密码', '', '', '#', '#', '', '', 'sys:user:resetPwd', 100, 0, 3, 8, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1008, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '角色列表', '', '', '#', '#', '', '', 'sys:role:list', 101, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1009, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '角色查询', '', '', '#', '#', '', '', 'sys:role:query', 101, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1010, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '角色新增', '', '', '#', '#', '', '', 'sys:role:save', 101, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1011, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '角色修改', '', '', '#', '#', '', '', 'sys:role:update', 101, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1012, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '角色删除', '', '', '#', '#', '', '', 'sys:role:remove', 101, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1013, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '角色导出', '', '', '#', '#', '', '', 'sys:role:export', 101, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1014, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '菜单列表', '', '', '#', '#', '', '', 'sys:resource:list', 102, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1015, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '菜单查询', '', '', '#', '#', '', '', 'sys:resource:query', 102, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1016, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '菜单新增', '', '', '#', '#', '', '', 'sys:resource:save', 102, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1017, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '菜单修改', '', '', '#', '#', '', '', 'sys:resource:update', 102, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1018, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '菜单删除', '', '', '#', '#', '', '', 'sys:resource:remove', 102, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1019, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '部门列表', '', '', '#', '#', '', '', 'sys:dept:list', 103, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1020, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '部门查询', '', '', '#', '#', '', '', 'sys:dept:query', 103, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1021, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '部门新增', '', '', '#', '#', '', '', 'sys:dept:save', 103, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1022, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '部门修改', '', '', '#', '#', '', '', 'sys:dept:update', 103, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1023, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '部门删除', '', '', '#', '#', '', '', 'sys:dept:remove', 103, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1024, 'System', '2022-08-04 15:31:10', 'admin', '2022-11-12 15:03:25', '岗位列表', '', '', '#', '#', '', '', 'sys:post:list', 1024, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1025, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '岗位查询', '', '', '#', '#', '', '', 'sys:post:query', 104, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1026, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '岗位新增', '', '', '#', '#', '', '', 'sys:post:save', 104, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1027, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '岗位修改', '', '', '#', '#', '', '', 'sys:post:update', 104, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1028, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '岗位删除', '', '', '#', '#', '', '', 'sys:post:remove', 104, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1029, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '岗位导出', '', '', '#', '#', '', '', 'sys:post:export', 104, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1030, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '字典列表', '', '', '#', '#', '', '', 'sys:dict:list', 105, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1031, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '字典查询', '', '', '#', '#', '', '', 'sys:dict:query', 105, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1032, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '字典新增', '', '', '#', '#', '', '', 'sys:dict:save', 105, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1033, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '字典修改', '', '', '#', '#', '', '', 'sys:dict:update', 105, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1034, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '字典删除', '', '', '#', '#', '', '', 'sys:dict:remove', 105, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1035, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '字典导出', '', '', '#', '#', '', '', 'sys:dict:export', 105, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1036, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '参数列表', '', '', '#', '#', '', '', 'sys:config:list', 106, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1037, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '参数查询', '', '', '#', '#', '', '', 'sys:config:query', 106, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1038, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '参数新增', '', '', '#', '#', '', '', 'sys:config:save', 106, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1039, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '参数修改', '', '', '#', '#', '', '', 'sys:config:update', 106, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1040, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '参数删除', '', '', '#', '#', '', '', 'sys:config:remove', 106, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1041, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '参数导出', '', '', '#', '#', '', '', 'sys:config:export', 106, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1042, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '公告列表', '', '', '#', '#', '', '', 'sys:notice:list', 107, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1043, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '公告查询', '', '', '#', '#', '', '', 'sys:notice:query', 107, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1044, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '公告新增', '', '', '#', '#', '', '', 'sys:notice:save', 107, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1045, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '公告修改', '', '', '#', '#', '', '', 'sys:notice:update', 107, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1046, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '公告删除', '', '', '#', '#', '', '', 'sys:notice:remove', 107, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1047, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '操作日志列表', '', '', '#', '#', '', '', 'sys:log:list', 500, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1048, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '操作清空', '', '', '#', '#', '', '', 'sys:log:clean', 500, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1049, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '操作删除', '', '', '#', '#', '', '', 'sys:log:remove', 500, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1050, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '日志导出', '', '', '#', '#', '', '', 'sys:log:export', 500, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1051, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '登录日志列表', '', '', '#', '#', '', '', 'sys:loginlog:list', 501, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1052, 'System', '2022-08-04 15:31:10', 'admin', '2022-11-12 15:03:25', '登录清空', '', '', '#', '#', '', '', 'sys:loginlog:clean', 501, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1053, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '登录删除', '', '', '#', '#', '', '', 'sys:loginlog:remove', 501, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1054, 'System', '2022-08-04 15:31:10', 'System', '2022-11-12 15:03:25', '日志导出', '', '', '#', '#', '', '', 'sys:loginlog:export', 501, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1055, 'System', '2022-08-04 15:31:10', 'System', '2024-05-30 21:54:00', '在线列表', '', '', '#', '#', '', '', 'monitor:online:list', 109, 0, 3, 1, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1056, 'System', '2022-08-04 15:31:10', 'System', '2024-05-30 21:54:25', '强退用户', '', '', '#', '#', '', '', 'monitor:online:forceLogout', 109, 0, 3, 4, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1057, 'System', '2022-08-04 15:31:10', 'System', '2022-08-12 15:12:09', '任务列表', '', '', '#', '#', '', '', 'monitor:job:list', 110, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1058, 'System', '2022-08-04 15:31:10', 'System', '2022-08-12 15:12:13', '任务查询', '', '', '#', '#', '', '', 'monitor:job:query', 110, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1059, 'System', '2022-08-04 15:31:10', 'System', '2022-08-12 15:12:16', '任务新增', '', '', '#', '#', '', '', 'monitor:job:save', 110, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1060, 'System', '2022-08-04 15:31:10', 'System', '2022-08-12 15:12:19', '任务修改', '', '', '#', '#', '', '', 'monitor:job:update', 110, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1061, 'System', '2022-08-04 15:31:10', 'System', '2022-08-12 15:12:22', '任务删除', '', '', '#', '#', '', '', 'monitor:job:remove', 110, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1062, 'System', '2022-08-04 15:31:10', 'System', '2022-08-12 15:12:25', '状态修改', '', '', '#', '#', '', '', 'monitor:job:changeStatus', 110, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1063, 'System', '2022-08-04 15:31:10', 'System', '2022-08-12 15:12:27', '任务导出', '', '', '#', '#', '', '', 'monitor:job:export', 110, 0, 3, 7, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1064, 'System', '2022-08-04 15:31:10', 'System', '2023-04-28 08:05:44', '生成列表', '', '', '#', '#', '', '', 'tool:gen:list', 116, 0, 3, 1, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1065, 'System', '2022-08-04 15:31:10', 'System', '2023-04-28 08:05:46', '生成查询', '', '', '#', '#', '', '', 'tool:gen:query', 116, 0, 3, 2, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1066, 'System', '2022-08-04 15:31:10', 'System', '2023-04-28 08:05:49', '生成修改', '', '', '#', '#', '', '', 'tool:gen:update', 116, 0, 3, 3, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1067, 'System', '2022-08-04 15:31:10', 'System', '2023-04-28 08:05:52', '生成删除', '', '', '#', '#', '', '', 'tool:gen:remove', 116, 0, 3, 4, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1068, 'System', '2022-08-04 15:31:10', 'System', '2023-04-28 08:05:55', '导入代码', '', '', '#', '#', '', '', 'tool:gen:import', 116, 0, 3, 5, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1069, 'System', '2022-08-04 15:31:10', 'System', '2023-04-28 08:06:01', '预览代码', '', '', '#', '#', '', '', 'tool:gen:preview', 116, 0, 3, 6, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1070, 'System', '2022-08-04 15:31:10', 'System', '2023-04-28 08:05:58', '生成代码', '', '', '#', '#', '', '', 'tool:gen:code', 116, 0, 3, 7, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1071, 'root', '2023-04-28 15:27:20', 'admin', '2023-04-28 15:27:20', '聊天管理', '', '', 'message', 'chat', '', '', '', 0, 0, 1, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1072, 'root', '2023-04-28 15:28:10', 'root', '2023-04-28 15:28:10', '聊天管理', '', '', '', 'chat', 'gpt/chat/index', '', '', 1071, 0, 2, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1073, 'root', '2023-04-28 15:28:40', 'root', '2023-04-28 15:28:40', '消息管理', '', '', '', 'chat-message', 'gpt/chat-message/index', '', '', 1071, 0, 2, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1074, 'root', '2023-04-28 15:33:57', 'root', '2023-04-28 15:33:57', '订单管理', '', '', 'money', 'order', '', '', '', 0, 0, 1, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1075, 'root', '2023-04-28 15:34:43', 'root', '2023-04-28 15:34:43', '订单管理', '', '', '', 'order', 'gpt/order/index', '', '', 1074, 0, 2, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1076, 'root', '2023-04-28 15:35:23', 'root', '2023-04-28 15:35:23', '兑换码管理', '', '', '', 'redemption', 'gpt/redemption/index', '', '', 1074, 0, 2, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1077, 'root', '2023-04-28 15:35:55', 'root', '2023-04-28 15:35:55', '会员中心', '', '', 'peoples', 'member', '', '', '', 0, 0, 1, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1078, 'root', '2023-04-28 15:36:12', 'root', '2023-04-28 15:36:12', '套餐配置', '', '', '', 'comb', 'gpt/comb/index', '', '', 1077, 0, 1, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1079, 'root', '2023-04-28 15:36:28', 'admin', '2023-04-28 15:36:28', '用户列表', '', '', '', 'member', 'gpt/member/index', '', '', 1077, 0, 2, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1080, 'root', '2023-04-28 15:37:10', 'root', '2023-04-28 15:37:10', '配置中心', '', '', 'tool', 'config', '', '', '', 0, 0, 1, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1081, 'root', '2023-04-28 15:38:41', 'root', '2023-04-28 15:38:41', 'token管理', '', '', '', 'openkey', 'gpt/openkey/index', '', '', 1080, 0, 2, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1082, 'root', '2023-04-28 15:43:30', 'root', '2023-04-28 15:43:30', '内容管理', '', '', '', 'content', 'gpt/content/index', '', '', 1080, 0, 2, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1083, 'root', '2023-04-28 15:44:20', 'admin', '2023-04-28 15:44:20', '站点配置', '', '', '', 'base', 'sys/base-config/index', '', '', 1080, 0, 2, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1084, 'root', '2023-04-28 15:44:35', 'root', '2023-12-26 06:56:53', '缩略图配置', '', '', '', 'upload', 'gpt/upload-config/index', '', '', 1080, 0, 2, 5, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1085, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:04:45', 'AI助理功能列表', '', '', '#', '#', '', '', 'gpt:assistant:list', 1169, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1086, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:04:45', 'AI助理功能查询', '', '', '#', '#', '', '', 'gpt:assistant:query', 1169, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1087, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:04:45', 'AI助理功能新增', '', '', '#', '#', '', '', 'gpt:assistant:save', 1169, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1088, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:04:45', 'AI助理功能修改', '', '', '#', '#', '', '', 'gpt:assistant:update', 1169, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1089, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:04:45', 'AI助理功能删除', '', '', '#', '#', '', '', 'gpt:assistant:remove', 1169, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1090, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:04:45', 'AI助理功能审核', '', '', '#', '#', '', '', 'gpt:assistant:audit', 1169, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1091, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:04:45', 'AI助理功能导出', '', '', '#', '#', '', '', 'gpt:assistant:export', 1169, 0, 3, 7, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1092, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:05', '基础配置列表', '', '', '#', '#', '', '', 'sys:base:config:list', 1083, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1093, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:05', '基础配置查询', '', '', '#', '#', '', '', 'sys:base:config:query', 1083, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1094, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:05', '基础配置新增', '', '', '#', '#', '', '', 'sys:base:config:save', 1083, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1095, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:06', '基础配置修改', '', '', '#', '#', '', '', 'sys:base:config:update', 1083, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1096, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:06', '基础配置删除', '', '', '#', '#', '', '', 'sys:base:config:remove', 1083, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1097, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:06', '基础配置审核', '', '', '#', '#', '', '', 'sys:base:config:audit', 1083, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1098, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:06', '基础配置导出', '', '', '#', '#', '', '', 'sys:base:config:export', 1083, 0, 3, 7, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1099, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:21', '聊天摘要列表', '', '', '#', '#', '', '', 'gpt:chat:list', 1072, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1100, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:21', '聊天摘要查询', '', '', '#', '#', '', '', 'gpt:chat:query', 1072, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1101, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:21', '聊天摘要新增', '', '', '#', '#', '', '', 'gpt:chat:save', 1072, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1102, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:21', '聊天摘要修改', '', '', '#', '#', '', '', 'gpt:chat:update', 1072, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1103, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:21', '聊天摘要删除', '', '', '#', '#', '', '', 'gpt:chat:remove', 1072, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1104, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:21', '聊天摘要审核', '', '', '#', '#', '', '', 'gpt:chat:audit', 1072, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1105, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:21', '聊天摘要导出', '', '', '#', '#', '', '', 'gpt:chat:export', 1072, 0, 3, 7, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1106, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:32', '对话消息列表', '', '', '#', '#', '', '', 'gpt:chat:message:list', 1073, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1107, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:32', '对话消息查询', '', '', '#', '#', '', '', 'gpt:chat:message:query', 1073, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1108, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:32', '对话消息新增', '', '', '#', '#', '', '', 'gpt:chat:message:save', 1073, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1109, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:32', '对话消息修改', '', '', '#', '#', '', '', 'gpt:chat:message:update', 1073, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1110, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:32', '对话消息删除', '', '', '#', '#', '', '', 'gpt:chat:message:remove', 1073, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1111, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:32', '对话消息审核', '', '', '#', '#', '', '', 'gpt:chat:message:audit', 1073, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1112, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:32', '对话消息导出', '', '', '#', '#', '', '', 'gpt:chat:message:export', 1073, 0, 3, 7, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1113, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:48', '会员套餐列表', '', '', '#', '#', '', '', 'gpt:comb:list', 1078, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1114, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:48', '会员套餐查询', '', '', '#', '#', '', '', 'gpt:comb:query', 1078, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1115, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:48', '会员套餐新增', '', '', '#', '#', '', '', 'gpt:comb:save', 1078, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1116, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:48', '会员套餐修改', '', '', '#', '#', '', '', 'gpt:comb:update', 1078, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1117, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:48', '会员套餐删除', '', '', '#', '#', '', '', 'gpt:comb:remove', 1078, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1118, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:48', '会员套餐审核', '', '', '#', '#', '', '', 'gpt:comb:audit', 1078, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1119, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 07:59:48', '会员套餐导出', '', '', '#', '#', '', '', 'gpt:comb:export', 1078, 0, 3, 7, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1120, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:00:29', '内容管理列表', '', '', '#', '#', '', '', 'gpt:content:list', 1082, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1121, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:00:29', '内容管理查询', '', '', '#', '#', '', '', 'gpt:content:query', 1082, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1122, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:00:29', '内容管理新增', '', '', '#', '#', '', '', 'gpt:content:save', 1082, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1123, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:00:29', '内容管理修改', '', '', '#', '#', '', '', 'gpt:content:update', 1082, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1124, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:00:29', '内容管理删除', '', '', '#', '#', '', '', 'gpt:content:remove', 1082, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1125, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:00:29', '内容管理审核', '', '', '#', '#', '', '', 'gpt:content:audit', 1082, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1126, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:00:29', '内容管理导出', '', '', '#', '#', '', '', 'gpt:content:export', 1082, 0, 3, 7, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1127, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:57:11', '文件管理列表', '', '', '#', '#', '', '', 'gpt:file:list', 1170, 0, 3, 1, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1128, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:57:13', '文件管理查询', '', '', '#', '#', '', '', 'gpt:file:query', 1170, 0, 3, 2, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1129, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:57:16', '文件管理新增', '', '', '#', '#', '', '', 'gpt:file:save', 1170, 0, 3, 3, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1130, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:56:56', '文件管理修改', '', '', '#', '#', '', '', 'gpt:file:update', 1170, 0, 3, 4, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1131, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:56:59', '文件管理删除', '', '', '#', '#', '', '', 'gpt:file:remove', 1170, 0, 3, 5, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1132, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:57:02', '文件管理审核', '', '', '#', '#', '', '', 'gpt:file:audit', 1170, 0, 3, 6, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1133, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:57:05', '文件管理导出', '', '', '#', '#', '', '', 'gpt:file:export', 1170, 0, 3, 7, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1134, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:39:40', '会员用户列表', '', '', '#', '#', '', '', 'gpt:user:list', 1079, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1135, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:39:43', '会员用户查询', '', '', '#', '#', '', '', 'gpt:user:query', 1079, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1136, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:39:46', '会员用户新增', '', '', '#', '#', '', '', 'gpt:user:save', 1079, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1137, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:39:50', '会员用户修改', '', '', '#', '#', '', '', 'gpt:user:update', 1079, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1138, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:39:52', '会员用户删除', '', '', '#', '#', '', '', 'gpt:user:remove', 1079, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1139, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:39:55', '会员用户审核', '', '', '#', '#', '', '', 'gpt:user:audit', 1079, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1140, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:40:00', '会员用户导出', '', '', '#', '#', '', '', 'gpt:user:export', 1079, 0, 3, 7, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1141, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:25', 'openai token列表', '', '', '#', '#', '', '', 'gpt:openkey:list', 1081, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1142, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:25', 'openai token查询', '', '', '#', '#', '', '', 'gpt:openkey:query', 1081, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1143, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:25', 'openai token新增', '', '', '#', '#', '', '', 'gpt:openkey:save', 1081, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1144, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:25', 'openai token修改', '', '', '#', '#', '', '', 'gpt:openkey:update', 1081, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1145, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:25', 'openai token删除', '', '', '#', '#', '', '', 'gpt:openkey:remove', 1081, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1146, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:25', 'openai token审核', '', '', '#', '#', '', '', 'gpt:openkey:audit', 1081, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1147, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:25', 'openai token导出', '', '', '#', '#', '', '', 'gpt:openkey:export', 1081, 0, 3, 7, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1148, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:41', '订单列表', '', '', '#', '#', '', '', 'gpt:order:list', 1075, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1149, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:41', '订单查询', '', '', '#', '#', '', '', 'gpt:order:query', 1075, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1150, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:41', '订单新增', '', '', '#', '#', '', '', 'gpt:order:save', 1075, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1151, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:41', '订单修改', '', '', '#', '#', '', '', 'gpt:order:update', 1075, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1152, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:41', '订单删除', '', '', '#', '#', '', '', 'gpt:order:remove', 1075, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1153, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:41', '订单审核', '', '', '#', '#', '', '', 'gpt:order:audit', 1075, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1154, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:01:41', '订单导出', '', '', '#', '#', '', '', 'gpt:order:export', 1075, 0, 3, 7, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1155, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:02:05', '兑换码列表', '', '', '#', '#', '', '', 'gpt:redemption:list', 1076, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1156, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:02:05', '兑换码查询', '', '', '#', '#', '', '', 'gpt:redemption:query', 1076, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1157, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:02:05', '兑换码新增', '', '', '#', '#', '', '', 'gpt:redemption:save', 1076, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1158, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:02:05', '兑换码修改', '', '', '#', '#', '', '', 'gpt:redemption:update', 1076, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1159, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:02:05', '兑换码删除', '', '', '#', '#', '', '', 'gpt:redemption:remove', 1076, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1160, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:02:05', '兑换码审核', '', '', '#', '#', '', '', 'gpt:redemption:audit', 1076, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1161, 'System', '2023-04-28 07:57:29', 'System', '2023-04-28 08:02:05', '兑换码导出', '', '', '#', '#', '', '', 'gpt:redemption:export', 1076, 0, 3, 7, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1162, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:56:28', '缩略图配置列表', '', '', '#', '#', '', '', 'gpt:upload:config:list', 1084, 0, 3, 1, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1163, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:56:30', '缩略图配置查询', '', '', '#', '#', '', '', 'gpt:upload:config:query', 1084, 0, 3, 2, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1164, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:56:32', '缩略图配置新增', '', '', '#', '#', '', '', 'gpt:upload:config:save', 1084, 0, 3, 3, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1165, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:56:35', '缩略图配置修改', '', '', '#', '#', '', '', 'gpt:upload:config:update', 1084, 0, 3, 4, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1166, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:56:43', '缩略图配置删除', '', '', '#', '#', '', '', 'gpt:upload:config:remove', 1084, 0, 3, 5, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1167, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:56:37', '缩略图配置审核', '', '', '#', '#', '', '', 'gpt:upload:config:audit', 1084, 0, 3, 6, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1168, 'System', '2023-04-28 07:57:29', 'System', '2023-12-26 06:56:51', '缩略图配置导出', '', '', '#', '#', '', '', 'gpt:upload:config:export', 1084, 0, 3, 7, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1169, 'root', '2023-04-28 16:04:01', 'admin', '2023-04-28 16:04:01', '助手管理', '', '', '', 'index', 'gpt/assistant/index', '', '', 1171, 0, 2, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1170, 'root', '2023-04-28 16:04:26', 'System', '2023-12-26 06:56:39', '文件列表', '', '', '', 'file', 'gpt/file/index', '', '', 1080, 0, 2, 6, 1, 0, 1);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1171, 'admin', '2023-11-22 11:05:52', 'admin', '2023-11-22 11:05:52', '助手中心', '', '', 'example', 'assistant', '', '', '', 0, 0, 1, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1172, 'admin', '2023-11-22 11:07:51', 'System', '2023-11-22 11:07:51', '助手分类', '', '', '', 'type', 'gpt/assistant-type/index', '', '', 1171, 0, 2, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1173, 'System', '2023-11-22 03:10:49', 'System', '2023-11-22 03:10:49', '助手分类列表', '', '', '#', '#', '', '', 'gpt:assistant:type:list', 1172, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1174, 'System', '2023-11-22 03:10:49', 'System', '2023-11-22 03:10:49', '助手分类查询', '', '', '#', '#', '', '', 'gpt:assistant:type:query', 1172, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1175, 'System', '2023-11-22 03:10:49', 'System', '2023-11-22 03:10:49', '助手分类新增', '', '', '#', '#', '', '', 'gpt:assistant:type:save', 1172, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1176, 'System', '2023-11-22 03:10:49', 'System', '2023-11-22 03:10:49', '助手分类修改', '', '', '#', '#', '', '', 'gpt:assistant:type:update', 1172, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1177, 'System', '2023-11-22 03:10:49', 'System', '2023-11-22 03:10:49', '助手分类删除', '', '', '#', '#', '', '', 'gpt:assistant:type:remove', 1172, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1178, 'System', '2023-11-22 03:10:49', 'System', '2023-11-22 03:10:49', '助手分类审核', '', '', '#', '#', '', '', 'gpt:assistant:type:audit', 1172, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1179, 'System', '2023-11-22 03:10:49', 'System', '2023-11-22 03:10:49', '助手分类导出', '', '', '#', '#', '', '', 'gpt:assistant:type:export', 1172, 0, 3, 7, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1180, 'System', '2024-03-18 13:52:44', 'admin', '2024-03-18 13:53:39', '大模型信息', '', '', '', 'model', 'gpt/model/index', '', '', 1080, 0, 2, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1181, 'System', '2024-03-18 13:52:45', 'System', '2024-03-18 13:52:45', '大模型信息列表', '', '', '#', '#', '', '', 'gpt:model:list', 1180, 0, 3, 1, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1182, 'System', '2024-03-18 13:52:45', 'System', '2024-03-18 13:52:45', '大模型信息查询', '', '', '#', '#', '', '', 'gpt:model:query', 1180, 0, 3, 2, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1183, 'System', '2024-03-18 13:52:45', 'System', '2024-03-18 13:52:45', '大模型信息新增', '', '', '#', '#', '', '', 'gpt:model:save', 1180, 0, 3, 3, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1184, 'System', '2024-03-18 13:52:45', 'System', '2024-03-18 13:52:45', '大模型信息修改', '', '', '#', '#', '', '', 'gpt:model:update', 1180, 0, 3, 4, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1185, 'System', '2024-03-18 13:52:45', 'System', '2024-03-18 13:52:45', '大模型信息删除', '', '', '#', '#', '', '', 'gpt:model:remove', 1180, 0, 3, 5, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1186, 'System', '2024-03-18 13:52:45', 'System', '2024-03-18 13:52:45', '大模型信息审核', '', '', '#', '#', '', '', 'gpt:model:audit', 1180, 0, 3, 6, 1, 0, 0);
INSERT INTO `sys_resource` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `title`, `code`, `icon`, `path`, `component`, `query`, `perms`, `parent_id`, `redirect`, `type`, `sort`, `status`, `hidden`, `deleted`) VALUES (1187, 'System', '2024-03-18 13:52:45', 'System', '2024-03-18 13:52:45', '大模型信息导出', '', '', '#', '#', '', '', 'gpt:model:export', 1180, 0, 3, 7, 1, 0, 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(128) NOT NULL DEFAULT '' COMMENT '名称',
  `code` varchar(128) NOT NULL DEFAULT '' COMMENT '角色编码',
  `data_scope` varchar(128) NOT NULL DEFAULT '' COMMENT '数据范围 1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限；5：仅本人数据权限',
  `remark` varchar(512) NOT NULL DEFAULT '' COMMENT '备注',
  `sort` int(1) NOT NULL DEFAULT '0' COMMENT '排序',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态 0->禁用;1->启用',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `name`, `code`, `data_scope`, `remark`, `sort`, `status`, `deleted`) VALUES (1, 'System', '2022-02-17 11:29:18', 'System', '2022-02-17 20:32:03', '超级管理员', 'admin', '1', '', 0, 1, 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `role_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色id',
  `resource_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '资源权限id',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态 0->禁用;1->启用',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `role_id` (`role_id`),
  KEY `resource_id` (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='角色权限';

-- ----------------------------
-- Records of sys_role_resource
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  `username` varchar(128) NOT NULL DEFAULT '' COMMENT '用户名/账号',
  `password` varchar(128) NOT NULL DEFAULT '' COMMENT '密码',
  `uid` varchar(128) NOT NULL DEFAULT '' COMMENT '用户标识',
  `name` varchar(128) NOT NULL DEFAULT '' COMMENT '姓名',
  `nick_name` varchar(128) NOT NULL DEFAULT '' COMMENT '昵称',
  `tel` varchar(32) NOT NULL DEFAULT '' COMMENT '手机号',
  `avatar` varchar(128) NOT NULL DEFAULT '' COMMENT '头像',
  `email` varchar(32) NOT NULL DEFAULT '' COMMENT '邮箱',
  `gender` int(1) NOT NULL DEFAULT '-1' COMMENT '性别 0->女;1->男',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态 0->禁用;1->启用',
  `admind` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否管理员 0->否;1->是',
  `deleted` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0->未删除;1->已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `uid` (`uid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统账号';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `login_time`, `username`, `password`, `uid`, `name`, `nick_name`, `tel`, `avatar`, `email`, `gender`, `status`, `admind`, `deleted`) VALUES (1, 'System', '2024-03-04 10:29:35', 'System', '2024-06-12 17:17:16', '2024-06-12 17:17:16', 'root', '$2a$10$ZJrHs1/UHizEmXbw1auMs.DR9pal5TVA.HoEQWQph7f63OUHozkbG', '2cb15d57-3b30-4b06-a04d-b8d32d234e8d', '超级管理员', '超级管理员', '13888888888', 'https://img1.baidu.com/it/u=1706103831,1363884341&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', '710957166@qq.com', 1, 1, 1, 0);
INSERT INTO `sys_user` (`id`, `create_user`, `create_time`, `update_user`, `update_time`, `login_time`, `username`, `password`, `uid`, `name`, `nick_name`, `tel`, `avatar`, `email`, `gender`, `status`, `admind`, `deleted`) VALUES (2, 'System', '2024-03-04 10:29:35', 'System', '2025-02-08 14:04:24', '2025-02-08 14:04:24', 'admin', '$2a$10$ZJrHs1/UHizEmXbw1auMs.DR9pal5TVA.HoEQWQph7f63OUHozkbG', '2cb15d57-3b30-4b06-a04d-b8d32d234e9D', '管理员', '管理员', '13888888888', 'https://img1.baidu.com/it/u=1706103831,1363884341&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500', '710957166@qq.com', 1, 1, 1, 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_dept`;
CREATE TABLE `sys_user_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `sys_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `dept_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户部门id',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态 0->禁用;1->启用',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `sys_user_id` (`sys_user_id`),
  KEY `dept_id` (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户部门关联';

-- ----------------------------
-- Records of sys_user_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_dept` (`id`, `create_user`, `create_time`, `sys_user_id`, `dept_id`, `status`) VALUES (1, 'System', '2022-07-21 10:03:11', 1, 1, 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_group`;
CREATE TABLE `sys_user_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `sys_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `group_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户组id',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态 0->禁用;1->启用',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `sys_user_id` (`sys_user_id`),
  KEY `group_id` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户组关联';

-- ----------------------------
-- Records of sys_user_group
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `sys_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `post_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户岗位id',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态 0->禁用;1->启用',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `sys_user_id` (`sys_user_id`),
  KEY `post_id` (`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户岗位关联';

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_user` varchar(32) NOT NULL DEFAULT 'System' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `sys_user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `role_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '角色id',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态 0->禁用;1->启用',
  PRIMARY KEY (`id`),
  KEY `sys_user_id` (`sys_user_id`),
  KEY `role_id` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='用户角色';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` (`id`, `create_user`, `create_time`, `sys_user_id`, `role_id`, `status`) VALUES (1, 'System', '2022-02-17 15:12:19', 1, 1, 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
