package com.example.webblog.exception

import com.example.webblog.model.Forums
import com.example.webblog.model.entity.ForumsEntity

class ForumAlreadyCreatedException(val forumsModel: Forums) : RuntimeException() {
}
