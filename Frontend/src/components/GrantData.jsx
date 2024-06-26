import React, { useState,useRef } from 'react';
import Select from 'react-select';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFilter } from '@fortawesome/free-solid-svg-icons';
import './GrantData.css'; // Make sure to import the CSS file

const GrantData = ({ data,onUploadSuccess }) => {
    const [selectedTags, setSelectedTags] = useState([]);
    const [showDropdown, setShowDropdown] = useState(false);
    const fileInputRef = useRef(null);
    const handleFileUpload = () => {
        if (fileInputRef.current) {
          fileInputRef.current.click();
        }
      };
    
      const handleFileChange = (event) => {
        const file = event.target.files[0];
        if (file) {
          const formData = new FormData();
          formData.append('file', file);
    
          fetch('http://localhost:8080/api/grantsubmissions/upload', {
            method: 'POST',
            body: formData
          })
          .then(response => {
            if (response.ok) {
              alert('File uploaded successfully!');
              onUploadSuccess();
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
    
    // Extract unique tags
    const allTags = Array.from(new Set(data.flatMap(item => item.tags))).map(tag => ({ value: tag, label: tag }));
  
    // Filter data based on selected tags
    const filteredData = selectedTags.length === 0
      ? data
      : data.filter(item => selectedTags.some(tag => item.tags.includes(tag.value)));
  
    // Toggle dropdown visibility
    const toggleDropdown = () => setShowDropdown(!showDropdown);
  
    return (
      <div className="table-container">
           <input type="file" accept=".csv" ref={fileInputRef} onChange={handleFileChange} style={{ display: 'none' }} />
      <button className="upload-button" onClick={handleFileUpload}>Upload Grants Info...</button>
        <table>
          <thead>
            <tr>
              <th>Grant ID</th>
              <th>Grant Submission Name</th>
              <th>Stage</th>
              <th>Foundation Owner Email</th>
              <th>Requested Amount</th>
              <th>Awarded Amount</th>
              <th>Grant Type</th>
              <th>Nonprofit Name</th>
              <th>
                <div className="tags-header">
                  <span>Tags</span>
                  <FontAwesomeIcon
                    icon={faFilter}
                    className="filter-icon"
                    color={selectedTags.length > 0 ? '#4CAF50' : '#000'}
                    onClick={toggleDropdown}
                  />
                  {showDropdown && (
                    <div className="dropdown-overlay">
                      <Select
                        isMulti
                        options={allTags}
                        onChange={setSelectedTags}
                        placeholder="Select tags..."
                        classNamePrefix="react-select"
                      />
                    </div>
                  )}
                </div>
              </th>
              <th>Duration Start</th>
              <th>Duration End</th>
            </tr>
          </thead>
          <tbody>
            {filteredData.map(item => (
              <tr key={item.id}>
                <td>{item.id}</td>
                <td>{item.grantSubmissionName}</td>
                <td>{item.stage}</td>
                <td>{item.foundationOwner.email}</td>
                <td>{item.requestedAmount}</td>
                <td>{item.awardedAmount}</td>
                <td>{item.grantType}</td>
                <td>{item.nonProfit.name}</td>
                <td>{item.tags.join(', ')}</td>
                <td>{item.durationStart}</td>
                <td>{item.durationEnd}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    );
  };
  
  export default GrantData;
