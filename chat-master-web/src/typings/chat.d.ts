declare namespace Chat {

	// 聊天列表信息
	interface History {
		title: string
		chatNumber: string
		createTime: string
	}

	// 模型信息
	interface Model {
		id: number
		name: string
		model: string
		icon: string
	}

	// 当前会话信息
	interface ChatState {
		active: string
		model: string
		usingContext: boolean;
		chat: { chatNumber: string; data: Chat[] }[]
	}

	// 聊天内容
	interface Message {
		messageId?: string | null,
		parentMessageId?: string | null,
		createTime: string
		content: string
		contentType: string
		model?: string
		role?: string
	}

	// 页面聊天内容
	interface Chat {
		conversationId?: string | null,
		parentMessageId?: string | null,
		dateTime: string
		contentType: string
		text: string
		images?: Array
		model?: string
		inversion?: boolean
		error?: boolean
		loading?: boolean
	}

	// 请求
	interface ChatRequest {
		assistantId?: number | undefined
		systemPrompt?: string
		prompt?: string
		model?: string
		modelVersion?: string
	}

	// 请求
	interface MessageRequest {
		chatNumber?: string
		systemPrompt?: string
		prompt?: string
		model?: string
		modelVersion?: string
	}

	// 返回
	interface ConversationResponse {
		id: string
		conversationId: string
		parentMessageId: string
		role: string
		content: string
		contentType: string
		detail: {
			choices: { finish_reason: string; index: number; logprobs: any; text: string }[]
			created: number
			id: string
			model: string
			object: string
			usage: { completion_tokens: number; prompt_tokens: number; total_tokens: number }
		}
	}
}
