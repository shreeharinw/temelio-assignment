import React, { useRef } from 'react';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import './GrantData.css';

const GrantData = ({ grants,onUploadSuccess }) => {
  const fileInputRef = useRef(null);

  const handleFileUpload = () => {
    // Trigger the file input click programmatically
    if (fileInputRef.current) {
      fileInputRef.current.click();
    }
  };

  const handleFileChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      // Assuming you are using FormData to send the file
      const formData = new FormData();
      formData.append('file', file);

      // Make POST API call to http://localhost:8080/api/grantsubmissions/upload
      fetch('http://localhost:8080/api/grantsubmissions/upload', {
        method: 'POST',
        body: formData
      })
      .then(response => {
        if (response.ok) {
          alert('File uploaded successfully!');
          onUploadSuccess();
          // Optionally, you may want to fetch updated data or update state after successful upload
        } else {
          alert('Failed to upload file. Please try again.');
        }
      })
      .catch(error => {
        console.error('Error uploading file:', error);
        alert('An error occurred while uploading the file. Please try again later.');
      });
    }
  };

  return (
    <div className="grant-table">
      <input type="file" accept=".csv" ref={fileInputRef} onChange={handleFileChange} style={{ display: 'none' }} />
      <button className="upload-button" onClick={handleFileUpload}>Upload Grants Info...</button>
      
      <DataTable value={grants} className="p-datatable-striped">
        <Column field="id" header="Grant ID" className="col-id" />
        <Column field="grantSubmissionName" header="Grant Submission Name" className="col-submission-name" />
        <Column field="stage" header="Stage" className="col-stage" />
        <Column field="foundationOwner.email" header="Foundation Owner Email" className="col-email" />
        <Column field="requestedAmount" header="Requested Amount" className="col-amount" />
        <Column field="awardedAmount" header="Awarded Amount" className="col-amount" />
        <Column field="grantType" header="Grant Type" className="col-grant-type" />
        <Column field="nonProfit.name" header="Nonprofit Name" className="col-nonprofit-name" />
        <Column field="tags" header="Tags" className="col-tags" />
        <Column field="durationStart" header="Duration Start" className="col-duration" />
        <Column field="durationEnd" header="Duration End" className="col-duration" />
      </DataTable>
    </div>
  );
};

export default GrantData;
