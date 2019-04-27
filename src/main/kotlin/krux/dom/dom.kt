package krux.dom

import krux.*
import org.w3c.dom.Element
import org.w3c.dom.HTMLDivElement
import kotlin.browser.document

fun <T> HasUpdateScheduler.createElement(tag: String, f: ElementBuilder<T>.() -> Unit): T {
    val elem = document.createElement(tag) as T
    val builder = elemBuilder(elem)
    builder.f()
    return elem
}

fun HasUpdateScheduler.div(f: ElementBuilder<HTMLDivElement>.() -> Unit): HTMLDivElement =
        createElement("div", f)

fun ElementBuilder<Element>.id(f: ReactiveContext.() -> String) =
        bind(Element::id, f)

fun ElementBuilder<Element>.className(f: ReactiveContext.() -> String) =
        bind(Element::className, f)

fun HasUpdateScheduler.test() =
    div {
        id { "test" }
        className { "x y z" }
    }
