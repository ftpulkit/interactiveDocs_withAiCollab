// Initialize Quill editor with custom toolbar
var quill = new Quill('#editor-container', {
    theme: 'snow',
    modules: {
        toolbar: {
            container: [
                [{ 'header': '1'}, { 'header': '2'}, { 'font': [] }],
                [{ 'list': 'ordered'}, { 'list': 'bullet' }],
                [{ 'align': [] }],
                ['bold', 'italic', 'underline'],
                ['link'],
                ['image'], // Adds image upload button to the toolbar
                [{ 'attach': 'file' }] // Custom attachment button
            ],
            handlers: {
                'image': function() { handleImageUpload(); }, // Quill's image handler
                'attach': function() { handleFileAttachment(); } // Custom handler
            }
        }
    }
});
function handleImageUpload() {
    // Create a hidden file input to trigger image selection
    let fileInput = document.createElement('input');
    fileInput.setAttribute('type', 'file');
    fileInput.setAttribute('accept', 'image/*'); // Accept only image files

    // Trigger file selection
    fileInput.click();

    // Handle the selected image file
    fileInput.onchange = () => {
        let file = fileInput.files[0];
        if (file) {
            // Create a FileReader to read the file content as a data URL
            let reader = new FileReader();
            reader.onload = function(event) {
                // Insert the image into the editor as a data URL
                let range = quill.getSelection();
                quill.insertEmbed(range.index, 'image', event.target.result);
                quill.setSelection(range.index + 1); // Move cursor after the image
            };
            reader.readAsDataURL(file); // Read file content as base64 data URL
        }
    };
}

function handleFileAttachment() {
    // Create a hidden file input
    let fileInput = document.createElement('input');
    fileInput.setAttribute('type', 'file');
    fileInput.setAttribute('accept', 'application/pdf, application/msword, text/plain, image/*'); // Accept specific types

    // Trigger file selection
    fileInput.click();

    // Handle the file once selected
    fileInput.onchange = () => {
        let file = fileInput.files[0];
        if (file) {
            alert(`File "${file.name}" selected!`);
            // Add functionality to upload or display file here
        }
    };
}

