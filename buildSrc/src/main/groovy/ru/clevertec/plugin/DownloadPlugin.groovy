package ru.clevertec.plugin

import ru.clevertec.task.ImageDownload
import org.gradle.api.Plugin
import org.gradle.api.Project

class DownloadPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        downloadimage(project)
    }

    void downloadimage(Project project) {
        def map = [description: "download task", group: "download", type: ImageDownload]
        project.task(map, "downloadimage") {
            sourceUrl = 'https://github.com/NepsterBY/receipt-generator/blob/feature/image%20receipt/receipt.png?raw=true'
            target = new File('src/main/resources/PurchaseReceipt.png')
        }
    }
}